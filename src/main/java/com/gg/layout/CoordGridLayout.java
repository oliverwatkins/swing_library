package com.gg.layout;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import javax.swing.JComponent;

/**
 * Like the {@link GridLayout}, this one aligns components based on a grid. But here, actual coordinates and bounds are given to the components and they will be
 * placed according to them. Think of it like <code>null</code> layout, but all bounds are measured in grid cells instead of pixels. Alternatively, this of it
 * as {@link GridBagLayout}, but in easy to use and more powerful. The layout of the grid cells is free and can be set to a specific aspect ratio, to a specific
 * size or to a fixed amount of rows and columns in the parent component.
 * 
 * The coordinates of the bounds of the child components must not be negative, but they their values might be so big that they get placed out of bounds. This
 * layout manager does not explicitly check to prevent this and will not throw any exception due to this. Components might also overlap each other, if this is
 * the case, please make sure that {@link JComponent#isOptimizedDrawingEnabled()} returns <code>false</code> in the parent component. As usual, the origin is
 * located at the top-left corner with positive y values pointing downwards and positive x value pointing to the right. This layout manager can't be shared. If
 * it is shared, it won't notice it and mostly behave normally, except that some calculations (the number of rows and columns and the preferred size) might
 * return too big results.
 * 
 * Because there are a lot of possibilities to set this layout manager up, this is not done through the constructor. Instead, the methods <code>set...</code>
 * provide this functionality. Each one represents a different operating mode with adjustable parameters. The constructor calls {@link #setFitBoth()} as default
 * setup.
 * 
 * @see GridLayout
 * @see GridBagLayout
 * @author piegames
 */
public class CoordGridLayout implements LayoutManager2, Serializable {

	protected static final int				FIXED_SIZE			= 0;
	protected static final int				FIXED_COUNT			= 1;
	protected static final int				FIT_HORIZONTAL		= 2;
	protected static final int				FIT_VERTICAL		= 3;
	protected static final int				FIT_BOTH			= 4;

	private static final long				serialVersionUID	= -9043905790608669068L;

	protected HashMap<Component, Rectangle>	comps				= new HashMap<Component, Rectangle>();

	protected Insets						insets;

	protected int							mode				= -1;
	protected int							rowSize				= -1;
	protected int							colSize				= -1;
	protected int							colCount			= -1;
	protected int							rowCount			= -1;
	protected double						aspectRatio			= -1;

	/**
	 * Creates a layout manager with no insets
	 * 
	 * @see #CoordGridLayout(Insets)
	 */
	public CoordGridLayout() {
		this(new Insets(0, 0, 0, 0));
	}

	/**
	 * Creates a layout manager with insets with the same amount of border in every direction.
	 * 
	 * @param insets the child component will keep that distance to the borders of its cell in each direction. This will result in doubled distance between two
	 *            components.
	 * @see #CoordGridLayout(Insets)
	 */
	public CoordGridLayout(int insets) {
		this(new Insets(insets, insets, insets, insets));
	}

	/**
	 * Creates a layout manager with the specified insets. Calls {@link #setFitBoth()} as initial setup.
	 * 
	 * @param insets all child components will keep that amount of border in each direction to the border of their grid cell.
	 */
	public CoordGridLayout(Insets insets) {
		this.insets = Objects.requireNonNull(insets);
		setFitBoth();
	}

	/**
	 * @see #setFixedSize(int, int)
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFixedSize(Dimension size) {
		setFixedSize(size.width, size.height);
		return this;
	}

	/**
	 * Initialized the grid where each cell has the specified width and the specified height.
	 * 
	 * @param cellWidth the width of each cell and the distance between two columns
	 * @param cellHeight the height of each cell and the distance between two rows
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFixedSize(int cellWidth, int cellHeight) {
		if (cellWidth <= 0 || cellHeight <= 0)
			throw new IllegalArgumentException("Dimensions must be > 0");
		mode = FIXED_SIZE;
		this.rowSize = cellWidth;
		this.colSize = cellHeight;
		this.colCount = 0;
		this.rowCount = 0;
		this.aspectRatio = Double.NaN;
		return this;
	}

	/**
	 * Calls {@link #setFixedCount(int, int)} with both parameters zero, meaning that row and column count will be calculated automatically.
	 * 
	 * @see #setFixedCount(int, int)
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFixedCount() {
		return setFixedCount(0, 0);
	}

	/**
	 * Will divide the panel into <code>rowCount</code> rows and <code>colCount</code> columns of equal size. The cell position <code>(rowCount|colCount)</code>
	 * will map to the bottom right corner of the parent component. All child components that exceed this size will be positioned out of bounds.
	 * 
	 * The values that are zero work as placeholder and will be replaced by the smallest number that fits all children onto the parent component during
	 * calculation.
	 * 
	 * @param rowCount the number of rows to divide the parent component into. If it is zero, the actual number will be calculated so that no child component
	 *            exceeds it parent's bounds horizontally.
	 * @param colCount the number of columns to divide the parent component into. If it is zero, the actual number will be calculated so that no child component
	 *            exceeds it parent's bounds vertically.
	 * @return <code>this</code>
	 * @throws IllegalArgumentException If one of the arguments is smaller than zero
	 */
	public CoordGridLayout setFixedCount(int rowCount, int colCount) {
		if (rowCount < 0 || colCount < 0)
			throw new IllegalArgumentException("Row and column count cannot be negative");
		mode = FIXED_COUNT;
		this.rowSize = -1;
		this.colSize = -1;
		this.colCount = rowCount;
		this.rowCount = colCount;
		this.aspectRatio = Double.NaN;
		return this;
	}

	/**
	 * Calls {@link #setFitHorizontal(int, double)} with <code>colCount</code> set to zero and an <code>aspectRatio</code> of one, meaning that the column count
	 * will be calculated automatically and a square grid.
	 * 
	 * @see #setFitHorizontal(int, double)
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFitHorizontal() {
		return setFitHorizontal(0, 1);
	}

	/**
	 * Calls {@link #setFitHorizontal(int, double)} with <code>colCount</code> set to zero, meaning that the column count will be calculated automatically.
	 * 
	 * @see #setFitHorizontal(int, double)
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFitHorizontal(double aspectRatio) {
		return setFitHorizontal(0, aspectRatio);
	}

	/**
	 * Like {@link #setFixedCount(int, int)}, but an aspect ratio is given instead of a row count. The number of rows will be adjusted automatically to keep the
	 * grid's cells at that specific aspect ratio. The cells will use the complete horizontal space, but vertically, space might be left or missing. All
	 * children will fit horizontally if they would with <code>setFixedCount</code>, but vertically, this cannot be guaranteed.
	 * 
	 * The aspect ratio of a cell is defined as height/width when positive and as width/height when negative.
	 * 
	 * @param colCount the number of columns to divide the parent component into. If it is zero, the actual number will be calculated so that no child component
	 *            exceeds it parent's bounds vertically.
	 * @param aspectRatio The aspect ratio of all cells as defined above
	 * @return <code>this</code>
	 * @throws IllegalArgumentException If the column count is smaller than zero, if the aspect ratio is zero, <code>NaN</code> or infinite.
	 */
	public CoordGridLayout setFitHorizontal(int colCount, double aspectRatio) {
		if (colCount < 0)
			throw new IllegalArgumentException("Column count cannot be negative");
		if (!Double.isFinite(aspectRatio) || aspectRatio == 0)
			throw new IllegalArgumentException("Aspect ratio must be a real non-zero number");
		if (aspectRatio < 0)
			aspectRatio = -1 / aspectRatio;
		mode = FIT_HORIZONTAL;
		this.rowSize = -1;
		this.colSize = -1;
		this.rowCount = 0;
		this.colCount = colCount;
		this.aspectRatio = aspectRatio;
		return this;
	}

	/**
	 * Calls {@link #setFitVertical(int, double)} with <code>rowCount</code> set to zero and an <code>aspectRatio</code> of one, meaning that the row count will
	 * be calculated automatically and a square grid.
	 * 
	 * @see #setFitVertical(int, double)
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFitVertical() {
		return setFitVertical(0, 1);
	}

	/**
	 * Calls {@link #setFitVertical(int, double)} with <code>rowCount</code> set to zero, meaning that the row count will be calculated automatically.
	 * 
	 * @see #setFitVertical(int, double)
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFitVertical(double aspectRatio) {
		return setFitVertical(0, aspectRatio);
	}

	/**
	 * Like {@link #setFixedCount(int, int)}, but an aspect ratio is given instead of a column count. The number of columns will be adjusted automatically to
	 * keep the grid's cells at that specific aspect ratio. The cells will use the complete vertical space, but horizontally, space might be left or missing.
	 * All children will fit vertically if they would with <code>setFixedCount</code>, but horizontally, this cannot be guaranteed.
	 * 
	 * The aspect ratio of a cell is defined as height/width when positive and as width/height when negative.
	 * 
	 * @param rowCount the number of rows to divide the parent component into. If it is zero, the actual number will be calculated so that no child component
	 *            exceeds it parent's bounds vertically.
	 * @param aspectRatio The aspect ratio of all cells as defined above
	 * @return <code>this</code>
	 * @throws IllegalArgumentException If the row count is smaller than zero, if the aspect ratio is zero, <code>NaN</code> or infinite.
	 */
	public CoordGridLayout setFitVertical(int rowCount, double aspectRatio) {
		if (rowCount < 0)
			throw new IllegalArgumentException("Row count cannot be negative");
		if (!Double.isFinite(aspectRatio) || aspectRatio == 0)
			throw new IllegalArgumentException("Aspect ratio must be a real non-zero number");
		if (aspectRatio < 0)
			aspectRatio = -1 / aspectRatio;
		mode = FIT_VERTICAL;
		this.rowSize = -1;
		this.colSize = -1;
		this.rowCount = rowCount;
		this.colCount = 0;
		this.aspectRatio = aspectRatio;
		return this;
	}

	/**
	 * Calls {@link #setFitBoth(int, int, double)} with a row and column count of zero meaning that those values will be calculated automatically. The aspect
	 * ratio will be set to one, resulting in square grid cells. This is the default setup for this layout manager.
	 * 
	 * @see #setFitBoth(int, int, double)
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFitBoth() {
		return setFitBoth(0, 0, 1);
	}

	/**
	 * Calls {@link #setFitBoth(int, int, double)} with a row and column count of zero meaning that those values will be calculated automatically.
	 * 
	 * @see #setFitBoth(int, int, double)
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFitBoth(double aspectRatio) {
		return setFitBoth(0, 0, aspectRatio);
	}

	/**
	 * A combination of {@link #setFixedCount(int, int)}, {@link #setFitHorizontal()} and {@link #setFitVertical()}. It will behave like either
	 * <code>setFitHorizontal</code> or like <code>setFitVertical</code> depending on the available amount of space to insure that no child component will
	 * exceed the parent component's bounds if it wouldn't width <code>setFixedCount</code>. All grid cells still keep the fixed aspect ratio.
	 * 
	 * @param rowCount the number of rows to divide the parent component into. If it is zero, the actual number will be calculated so that no child component
	 *            exceeds it parent's bounds horizontally.
	 * @param colCount the number of columns to divide the parent component into. If it is zero, the actual number will be calculated so that no child component
	 *            exceeds it parent's bounds vertically.
	 * @param aspectRatio The aspect ratio of all cells as defined above
	 * @return <code>this</code>
	 */
	public CoordGridLayout setFitBoth(int rowCount, int colCount, double aspectRatio) {
		if (rowCount < 0 || colCount < 0)
			throw new IllegalArgumentException("Row and column count cannot be negative");
		if (!Double.isFinite(aspectRatio) || aspectRatio == 0)
			throw new IllegalArgumentException("Aspect ratio must be a real non-zero number");
		if (aspectRatio < 0)
			aspectRatio = -1 / aspectRatio;
		mode = FIT_BOTH;
		this.rowSize = -1;
		this.colSize = -1;
		this.colCount = colCount;
		this.rowCount = rowCount;
		this.aspectRatio = aspectRatio;
		return this;
	}

	/**
	 * @param comp The child component to be to the parent. If it is already present, its bounds will be updated.
	 * @param constraints specifies the position of the child object on the grid. Must be either a <code>Point</code>, a <code>Dimension</code>, a
	 *            <code>Rectangle</code> or a <code>Shape</code>. If it is one of the first two, the value will be transformed to a Rectangle with a default
	 *            dimension of 1x1 and a default position of (0|0). If it is a Shape, the shape's bounds will be taken as position for the child object. Note
	 *            that all values don't represent pixels, but grid cell amounts.
	 * @throws IllegalArgumentException if the <code>constraints</code> is not instance of one of the above.
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(constraints);
		if (constraints instanceof Rectangle) {
			Rectangle d = (Rectangle) constraints;
			if (d.x < 0 || d.y < 0)
				throw new IllegalArgumentException("No negative positions allowed");
			if (d.width <= 0 || d.height <= 0)
				throw new IllegalAccessError("Size must be positive");
			comps.put(comp, d);
		} else if (constraints instanceof Point) {
			addLayoutComponent(comp, new Rectangle((Point) constraints, new Dimension(1, 1)));
		} else if (constraints instanceof Dimension) {
			addLayoutComponent(comp, new Rectangle((Dimension) constraints));
		} else if (constraints instanceof Shape) {
			addLayoutComponent(comp, ((Shape) constraints).getBounds());
		} else {
			throw new IllegalArgumentException("Cannot add to layout: constraints must be a Rectangle, a Point or a Shape - " + constraints.getClass() + " found");
		}
	}

	@Override
	@Deprecated
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("This method is deprecated");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		comps.keySet().remove(comp);
	}

	@Override
	public void layoutContainer(Container cParent) {
		Dimension parent = cParent.getSize();
		Insets i = cParent.getInsets();
		parent.width -= i.left + i.right;
		parent.height -= i.top + i.bottom;

		Dimension d = new Dimension();
		Rectangle[] children = comps.values().toArray(new Rectangle[0]);
		Dimension gridDim = getGridCellCount(children);
		int rowCount = this.rowCount == 0 ? gridDim.height : this.rowCount;
		int colCount = this.colCount == 0 ? gridDim.width : this.colCount;
		switch (mode) {
			case FIXED_SIZE:
				d.width = rowSize;
				d.height = colSize;
				break;
			case FIXED_COUNT:
				d.width = parent.width / colCount;
				d.height = parent.height / rowCount;
				break;
			case FIT_HORIZONTAL: {
				double width = (double) parent.width / colCount;
				double width2 = keepRows(width);
				d.width = (int) width;
				d.height = (int) width2;
			}
				break;
			case FIT_VERTICAL: {
				double height = (double) parent.height / rowCount;
				double height2 = keepCols(height);
				d.width = (int) height2;
				d.height = (int) height;
			}
				break;
			case FIT_BOTH: {
				double width = (double) parent.width / colCount;
				double height = (double) parent.height / rowCount;
				double width2 = keepRows(width);
				double height2 = keepCols(height);
				d.width = (int) (width * width2 < height2 * height ? width : height2);
				d.height = (int) (width * width2 < height2 * height ? width2 : height);
			}
				break;
		}
		for (java.util.Map.Entry<Component, Rectangle> entry : comps.entrySet())
			layoutComponent(entry.getKey(), entry.getValue(), i.left, i.top, d.width, d.height);
	}

	protected double keepRows(double width) {
		// r = y / x <==> y = r * x
		return aspectRatio * width;
	}

	protected double keepCols(double height) {
		// r = y / x <==> x = y / r
		return height / aspectRatio;
	}

	protected void layoutComponent(Component comp, Rectangle r, int ox, int oy, int w, int h) {
		comp.setBounds(r.x * w + insets.left + ox, r.y * h + insets.top + oy, r.width * w - insets.left - insets.right, r.height * h - insets.top - insets.bottom);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Override
	public Dimension preferredLayoutSize(Container cParent) {
		Dimension ret = new Dimension();
		Rectangle[] children = comps.values().toArray(new Rectangle[0]);

		Dimension gridDim = getGridCellCount(children);
		int rowCount = this.rowCount == 0 ? gridDim.height : this.rowCount;
		int colCount = this.colCount == 0 ? gridDim.width : this.colCount;
		if (mode == FIXED_SIZE) {
			ret.width = rowSize * colCount;
			ret.height = colSize * rowCount;
		} else {
			int count = 0;
			for (int y = 0; y < colCount; y++)
				for (int x = 0; x < rowCount; x++)
					for (Entry<Component, Rectangle> e : comps.entrySet()) {
						if (e.getValue().contains(x, y)) {
							count++;
							Dimension d = e.getKey().getPreferredSize();
							ret.width += d.width;
							ret.height += d.height;
						}
					}
			// ret contains now the sum of the preferred size of all cells
			if (count > 0) {
				ret.width = ret.width * colCount / count;
				ret.height = ret.height * rowCount / count;
			}
			// Divide by the number of cells to get the average preferred cell size
			// Multiply with the grid's dimension to get the preferred size.

			// Eventually adjust depending on the aspect ratio setting
			switch (mode) {
				case FIXED_COUNT:
					ret.setSize(ret);
					break;
				case FIT_HORIZONTAL: {
					double width = ret.width;
					double width2 = keepRows(width);
					ret.width = (int) width;
					ret.height = (int) width2;
				}
					break;
				case FIT_VERTICAL: {
					double height = ret.height;
					double height2 = keepCols(height);
					ret.width = (int) height2;
					ret.height = (int) height;
				}
					break;
				case FIT_BOTH: {
					double width = ret.width;
					double height = ret.height;
					double width2 = keepRows(width);
					double height2 = keepCols(height);
					ret.width = (int) (width * width2 < height2 * height ? width : height2);
					ret.height = (int) (width * width2 < height2 * height ? width2 : height);
				}
					break;
			}
		}
		Insets i = cParent.getInsets();
		ret.width += i.left + i.right;
		ret.height += i.top + i.bottom;
		return ret;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(1, 1);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	public static Dimension getGridCellCount(Rectangle[] children) {
		Dimension ret = new Dimension();
		ret.width = Arrays.stream(children).map(r -> r.x + r.width).max((x, y) -> Integer.compare(x, y)).orElse(0);
		ret.height = Arrays.stream(children).map(r -> r.y + r.height).max((x, y) -> Integer.compare(x, y)).orElse(0);
		return ret;
	}
}
