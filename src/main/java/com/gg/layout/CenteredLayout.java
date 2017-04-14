package com.gg.layout;

import java.awt.*;

/**
 * A <code>CenteredLayout</code> places its child component at the center of the parent component. The size can be specified through the scale mode and uses the
 * child component's preferred size as reference. This layout manager extends the <code>CardLayout</code>, if you add more than one component to your container,
 * only one will be shown as handled by the CardLayout.
 * 
 * @author piegames
 */
public class CenteredLayout extends CardLayout {

	private static final long	serialVersionUID	= -3078717704838021684L;

	/**
	 * This setting indicates that child components will keep their preferred size independently of the place available in the parent component.
	 */
	public static final int		KEEP_SIZE			= 0;

	/**
	 * This setting indicates that if there is not enough space in the parent component, the child will be scaled down while preserving its height/width aspect
	 * ratio to make it fit the container.
	 */
	public static final int		DOWNSCALE			= 1;

	/**
	 * This setting indicates that the child component will be scaled up or down while preserving its height/with aspect ratio depending on whether more or less
	 * space than needed is available.
	 */
	public static final int		RESCALE				= 2;

	/**
	 * This setting indicates that the child component should inherit the parent component's size, as done by default in the <code>CardLayout</code> manager.
	 */
	public static final int		MAXIMIZE			= 3;

	/**
	 * This value specifies how the child components will be sized. It must be one of <code>KEEP_SIZE</code>, <code>DOWNSCALE</code>, <code>RESCALE</code> or
	 * <code>MAXIMIZE</code>.
	 * 
	 * @see #setMode()
	 * @see #getMode(int)
	 */
	protected int				mode;

	public CenteredLayout(int mode) {
		this(mode, 0, 0);
	}

	public CenteredLayout(int mode, int hgap, int vgap) {
		super(hgap, vgap);
		setMode(mode);
	}

	/**
	 * Sets the new scaling mode for components. Must be one of: {@link #KEEP_SIZE}, {@link #DOWNSCALE}, {@link #RESCALE} or {@link #MAXIMIZE}.
	 * 
	 * @param mode the new value
	 * @throws IllegalArgumentException if the new value is not one of the above
	 * @see #mode
	 */
	public void setMode(int mode) {
		if (mode < 0 || mode > 3)
			throw new IllegalArgumentException("Illegal mode: " + mode);
		this.mode = mode;
	}

	/**
	 * Returns the setting currently used to align components when there is space leftover. Returns one of: {@link #KEEP_SIZE}, {@link #DOWNSCALE},
	 * {@link #RESCALE} or {@link #MAXIMIZE}.
	 * 
	 * @return the <code>mode</code> setting
	 * @see #mode
	 */
	public int getMode() {
		return mode;
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			int ncomponents = parent.getComponentCount();
			Component comp = null;
			boolean currentFound = false;

			for (int i = 0; i < ncomponents; i++) {
				comp = parent.getComponent(i);
				layoutComponent(parent, comp);
				if (comp.isVisible()) {
					currentFound = true;
				}
			}

			if (!currentFound && ncomponents > 0) {
				parent.getComponent(0).setVisible(true);
			}
		}
	}

	protected void layoutComponent(Container cParent, Component comp) {
		int vgap = getVgap(), hgap = getHgap();
		Insets insets = cParent.getInsets();
		insets.left += hgap;
		insets.right += hgap;
		insets.top += vgap;
		insets.bottom += vgap;
		Dimension parent = cParent.getSize();
		Point center = new Point(parent.width / 2, parent.height / 2);
		parent.width -= insets.right + insets.left;
		parent.height -= insets.top + insets.bottom;
		Dimension preferred = comp.getPreferredSize();

		float scale = 1;
		switch (mode) {
			case 0:
				comp.setBounds(
						center.x - preferred.width / 2,
						center.y - preferred.height / 2,
						preferred.width,
						preferred.height);
				break;
			case 1:
				if (preferred.width > parent.width)
					scale = (float) parent.width / preferred.width;
				if (preferred.height > parent.height)
					scale = Math.min(scale, (float) parent.height / preferred.height);
				preferred.width *= scale;
				preferred.height *= scale;
				comp.setBounds(
						center.x - preferred.width / 2,
						center.y - preferred.height / 2,
						preferred.width,
						preferred.height);
				break;
			case 2:
				scale = (float) parent.width / preferred.width;
				scale = Math.min(scale, (float) parent.height / preferred.height);
				preferred.width *= scale;
				preferred.height *= scale;
				comp.setBounds(
						center.x - preferred.width / 2,
						center.y - preferred.height / 2,
						preferred.width,
						preferred.height);
				break;
			case 3:
				comp.setBounds(
						insets.left,
						insets.top,
						parent.width,
						parent.height);
				break;
		}
	}
}