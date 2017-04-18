package com.gg.layout;

import static javax.swing.BoxLayout.*;
import java.awt.*;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import javax.swing.BoxLayout;

/**
 * Places components like {@link FlowLayout} in the {@code axis} direction and like a {@link BoxLayout} in the orthogonal direction.
 * 
 * Components parallel to the axis direction will be put next to each other according to their preferred size like the FlowLayout does. If the length exceeds
 * the parent component's length, all components will be scaled down equally in that direction. If the parent component provides more space than needed, the
 * remaining space at the bottom or at the right will be left empty.
 * 
 * Their other dimension will be maximized like the BoxLayout does and is guaranteed to correspond to the parent's size.
 *
 * @author piegames
 */
public class BoxFlowLayout implements LayoutManager, Serializable {

	private static final long	serialVersionUID	= -1510756179837815741L;

	/**
	 * This value indicates that all components should be left-justified on horizontal alignment and top-justified on vertical alignment.
	 */
	public static final int		LEADING				= 0;

	/**
	 * This value indicates that all components should be right-justified on horizontal alignment and bottom-justified on vertical alignment.
	 */
	public static final int		TRAILING			= 1;

	/**
	 * This value indicates that all components should be centered.
	 */
	public static final int		CENTER				= 2;

	/**
	 * This value indicates that all components should spread out equally to fill the space. Since their size is not changed by the <code>align</code> setting,
	 * this will result in gaps between the components. If you don't want them, use a <code>BoxLayout</code> instead since it would reproduce that layout's
	 * behavior.
	 */
	public static final int		SPREAD				= 3;

	/**
	 * If the components take less space in the axis direction than the parent component provides, this value indicated how they should be placed. It must be
	 * one of <code>LEADING</code>, <code>TRAILING</code>, <code>CENTER</code> or <code>SPREAD</code>. This setting does not affect the child component's size.
	 * 
	 * @see #getAlign()
	 * @see #setAlign(int)
	 */
	protected int				align;

	/**
	 * Acts like the axis of a {@link BoxLayout}.
	 * 
	 * @see #getAxis()
	 * @see #setAxis(int)
	 */
	protected int				axis;

	/**
	 * The gap between two components acts mostly like <code>vgap</code> in the {@link FlowLayout} if the alignment is horizontal and like <code>hgap</code> if
	 * the alignment is vertical. <code>gap</code> only specifies the gap between child components and not between child components and their parent. This
	 * property is defined by the parent's insets.
	 * 
	 * @see #getGap()
	 * @see #setGap(int)
	 */
	protected int				gap;

	/**
	 * Creates a new <code>BoxFlowLayout</code> that will lay out components along the vertical axis with a 5 units gap between components and
	 * <code>LEADING</code> alignment for unused space.
	 * 
	 * @see #BoxFlowLayout(int, int, int)
	 */
	public BoxFlowLayout() {
		this(BoxLayout.Y_AXIS, 5, LEADING);
	}

	/**
	 * Creates a new <code>BoxFlowLayout</code> that will lay out components along the given axis with a 5 units gap between components and <code>LEADING</code>
	 * alignment for unused space.
	 * 
	 * @see #BoxFlowLayout(int, int, int)
	 */
	@ConstructorProperties({ "axis" })
	public BoxFlowLayout(int axis) {
		this(axis, 5, LEADING);
	}

	/**
	 * Creates a new <code>BoxFlowLayout</code> that will lay out components along the given axis with a given gap between components and <code>LEADING</code>
	 * alignment for unused space.
	 * 
	 * @see #BoxFlowLayout(int, int, int)
	 */
	@ConstructorProperties({ "axis", "gap" })
	public BoxFlowLayout(int axis, int gap) {
		this(axis, gap, LEADING);
	}

	/**
	 * Creates a new <code>BoxFlowLayout</code> with the given properties. The constructor only calls the setters methods.
	 * 
	 * @see #setAxis(int)
	 * @see #setGap(int)
	 * @see #setAlign(int)
	 */
	@ConstructorProperties({ "axis", "gap", "align" })
	public BoxFlowLayout(int axis, int gap, int align) {
		setAxis(axis);
		setGap(gap);
		setAlign(align);
	}

	/**
	 * Sets the new alignment of components. Must be one of: {@link #LEADING}, {@link #TRAILING}, {@link #CENTER} or {@link #SPREAD}.
	 * 
	 * @param align the new value
	 * @throws IllegalArgumentException if the new value is not one of the above
	 * @see #align
	 */
	public void setAlign(int align) {
		if (align < 0 || align > 3)
			throw new IllegalArgumentException("Invalid align: " + align);
		this.align = align;
	}

	/**
	 * Returns the setting currently used to align components when there is space leftover. Returns one of: {@link #LEADING}, {@link #TRAILING}, {@link #CENTER}
	 * or {@link #SPREAD}.
	 * 
	 * @return the <code>align</code> setting
	 * @see #align
	 */
	public int getAlign() {
		return align;
	}

	/**
	 * Specifies a new value for the gap between two components.
	 * 
	 * @param gap the new value. Must not be negative.
	 * @see #gap
	 * @throws IllegalArgumentException if the new value is negative
	 */
	public void setGap(int gap) {
		if (gap < 0)
			throw new IllegalArgumentException("Invalid gap: " + align);
		this.gap = gap;
	}

	/**
	 * @return the amount of space added between components
	 * @see #gap
	 */
	public int getGap() {
		return gap;
	}

	/**
	 * Sets the axis that was used to lay out components. Must be one of: {@link BoxLayout#X_AXIS}, {@link BoxLayout#Y_AXIS}, {@link BoxLayout#LINE_AXIS} or
	 * {@link BoxLayout#PAGE_AXIS}.
	 * 
	 * @param axis the new axis to orient the components
	 * @throws IllegalArgumentException if the new value is not one of the above
	 */
	public void setAxis(int axis) {
		if (axis < 0 || axis > 3)
			throw new IllegalArgumentException("Invalid axis: " + axis);
		this.axis = axis;
	}

	/**
	 * Returns the axis that was used to lay out components. Returns one of: {@link BoxLayout#X_AXIS}, {@link BoxLayout#Y_AXIS}, {@link BoxLayout#LINE_AXIS} or
	 * {@link BoxLayout#PAGE_AXIS}.
	 *
	 * @return the axis that is used to lay out components
	 */
	public int getAxis() {
		return axis;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Component[] subs = parent.getComponents();
		int w = 0;
		int h = 0;
		Insets insets = parent.getInsets();

		if (isHorizontal(parent)) {
			w += insets.left + insets.right;
			for (Component c : subs) {
				w += c.getPreferredSize().width + gap;
				h = Math.max(h, c.getPreferredSize().height);
			}
			w -= gap;
		} else {
			h += insets.top + insets.bottom;
			for (Component c : subs) {
				w = Math.max(w, c.getPreferredSize().width);
				h += c.getPreferredSize().height + gap;
			}
			h -= gap;
		}

		return new Dimension(w, h);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Component[] subs = parent.getComponents();
		int w = 0;
		int h = 0;
		Insets insets = parent.getInsets();

		if (isHorizontal(parent)) {
			w += insets.left + insets.right;
			for (Component c : subs) {
				w += c.getMinimumSize().width + gap;
				h = Math.max(h, c.getMinimumSize().height);
			}
			if (subs.length > 0)
				w -= gap;
		} else {
			h += insets.top + insets.bottom;
			for (Component c : subs) {
				w = Math.max(w, c.getMinimumSize().width);
				h += c.getMinimumSize().height + gap;
			}
			if (subs.length > 0)
				h -= gap;
		}
		return new Dimension(w, h);
	}

	@Override
	public void layoutContainer(Container parent) {
		Dimension preferred = preferredLayoutSize(parent);
		// Dimension minimum = minimumLayoutSize(parent);
		// TODO scale down base on the minimum size

		Component[] subs = parent.getComponents();
		Insets insets = parent.getInsets();
		int width = parent.getWidth() - insets.left - insets.right;
		int height = parent.getHeight() - insets.top - insets.bottom;
		double x = 0;
		double y = 0;
		x += insets.left;
		y += insets.top;
		double scale = 1;
		double gap = this.gap;

		if (isHorizontal(parent)) {
			if (parent.getWidth() < preferred.getWidth())
				scale = parent.getWidth() / preferred.getWidth();
			else if (parent.getWidth() > preferred.getWidth()) {
				double leftover = parent.getWidth() - preferred.getWidth();
				switch (align) {
					case LEADING:
						break;
					case TRAILING:
						x += leftover;
						break;
					case CENTER:
						x += leftover / 2;
						break;
					case SPREAD:
						gap += leftover / (subs.length - 1);
						break;
				}
			}
			for (Component c : subs) {
				double w = c.getPreferredSize().width * scale;
				c.setBounds((int) x, (int) y, (int) w, height);
				x += w + gap;
			}
		} else {
			if (parent.getHeight() < preferred.getHeight())
				scale = parent.getHeight() / preferred.getHeight();
			else if (parent.getHeight() > preferred.getHeight()) {
				double leftover = parent.getHeight() - preferred.getHeight();
				switch (align) {
					case LEADING:
						break;
					case TRAILING:
						y += leftover;
						break;
					case CENTER:
						y += leftover / 2;
						break;
					case SPREAD:
						if (subs.length > 1)
							gap += leftover / (subs.length - 1);
						break;
				}
			}
			for (Component c : subs) {
				double h = c.getPreferredSize().height * scale;
				c.setBounds((int) x, (int) y, width, (int) h);
				y += h + gap;
			}
		}
	}

	protected boolean isHorizontal(Container parent) {
		return resolveAxis(parent) == X_AXIS;
	}

	protected boolean isVertical(Container parent) {
		return resolveAxis(parent) == Y_AXIS;
	}

	protected int resolveAxis(Container parent) {
		ComponentOrientation o = parent.getComponentOrientation();
		int absoluteAxis;
		if (axis == LINE_AXIS) {
			absoluteAxis = o.isHorizontal() ? X_AXIS : Y_AXIS;
		} else if (axis == PAGE_AXIS) {
			absoluteAxis = o.isHorizontal() ? Y_AXIS : X_AXIS;
		} else {
			absoluteAxis = axis;
		}
		return absoluteAxis;
	}
}