
/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.vaadin.addons.layouts;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * A simple layout that shows only one component at a time and provides a means to "flip through" the components changing the
 * visibility
 */
public class CardLayout extends CssLayout {

    private static final long serialVersionUID = 1621705379104148350L;

    /**
     * Index of the currently visible component
     */
    private int currentCard;

    /**
     * {@inheritDoc}
     *
     * If there are no components present then this component is made the visible component; otherwise, it is marked as invisible.
     */
    @Override
    public void addComponent(Component c) {
        boolean visible = this.isEmpty();
        super.addComponent(c);
        c.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     *
     * If there was a component already present at the beginning of the layout it is made invisible and the newly added component
     * is made visible.
     */
    @Override
    public void addComponentAsFirst(Component c) {
        boolean visible = this.currentCard == 0;
        if (visible && !this.isEmpty()) {
            Component existing = this.getComponent(0);
            if (existing != null)
                existing.setVisible(false);
        }
        super.addComponentAsFirst(c);
        c.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     *
     * If there was a component already the specified index it is made invisible and the newly added component is made visible.
     */
    @Override
    public void addComponent(Component c, int index) {
        boolean visible = this.currentCard == index;
        if (visible) {
            Component existing = this.getComponent(index);
            if (existing != null)
                existing.setVisible(false);
        }
        super.addComponent(c, index);
        c.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     *
     * If the removed component was the visible component the component now at the removed component's previous index in the layout
     * is set as the new visible component.  If the removed component was the last in the list the component previous to the removed
     * component is made the visible component.
     */
    @Override
    public void removeComponent(Component c) {
        this.replaceOrRemoveComponent(c, null);
    }

    /**
     * {@inheritDoc}
     *
     * If the old component was the visible component the component now at the old component's previous index in the layout
     * is set as the new visible component.
     */
    @Override
    public void replaceComponent(Component oldComponent, Component newComponent) {
        this.replaceOrRemoveComponent(oldComponent, newComponent);
    }

    private void replaceOrRemoveComponent(Component oldComponent, Component newComponent) {
        int index = this.getComponentIndex(oldComponent);
        if (index < 0)
            return;
        if (newComponent != null) {
            super.replaceComponent(oldComponent, newComponent);
        } else {
            super.removeComponent(oldComponent);
        }
        if (this.isEmpty()) {
            this.currentCard = 0;
            return;
        }
        if (index == this.currentCard) {
            if (index == this.size()) {
                index--;
            }
            this.setVisible(index);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllComponents() {
        super.removeAllComponents();
        this.currentCard = 0;
    }

    /**
     * Alias for {#link getComponentCount}
     * @return number of components in layout
     */
    public int size() {
        return this.getComponentCount();
    }

    /**
     * Whether or not there are any components in this layout
     * @return true if layout is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Sets the first component in the layout as the visible component.
     */
    public void first() {
        if (this.isEmpty())
            return;
        this.setVisible(0);
    }

    /**
     * Sets the last component in the layout as the visible component.
     */
    public void last() {
        if (this.isEmpty())
            return;
        this.setVisible(this.getComponentCount() - 1);
    }

    /**
     * Sets the next component in the layout as the visible component. If the current component is the last in the layout this
     * method will wrap around and make the first component in the layout visible.
     */
    public void next() {
        int index = this.currentCard + 1;
        if (index >= size())
            index = 0;
        this.setVisible(index);
    }

    /**
     * Sets the previous component in the layout as the visible component. If the current component is the first in the layout this
     * method will wrap around and make the last component in the layout visible.
     */
    public void previous() {
        int index = this.currentCard - 1;
        if (index < 0)
            index = this.size() - 1;
        this.setVisible(index);
    }

    /**
     * Makes the component at the specified index visible and all other invisible
     * @param index index of component to make visible
     * @throws IndexOutOfBoundsException if specified index is out of bounds (e.g. [0, count - 1])
     */
    public void show(int index) {
        this.setVisible(index);
    }

    /**
     * Makes the specified component visible and all other invisible. If the specified component is null or has not yet been added
     * to this layout then this operation is a no-op
     * @param c component to make visible
     */
    public void show(Component c) {
        if (c == null)
            return;
        int index = this.getComponentIndex(c);
        if (index < 0)
            return;
        this.show(index);
    }

    /**
     * Returns the index of the currently visible component, if any
     * @return index of the currently visible component or -1 if no components are present
     */
    public int getVisibleComponentIndex() {
        if (this.isEmpty())
            return -1;
        return this.currentCard;
    }

    /**
     * Retrieves the currently visible component, if any
     * @return currently visible component or null is no components are present
     */
    public Component getVisibleComponent() {
        if (this.isEmpty())
            return null;
        return this.getComponent(this.currentCard);
    }

    /**
     * Retrieves the visibility of the component at the specified index
     * @param index position of component in this container
     * @return true if component at index is visible, false otherwise
     * @throws IndexOutOfBoundsException if index is out of bounds (e.g. [0, count - 1])
     */
    public boolean isComponentVisible(int index) {
        this.checkBounds(index);
        return this.getComponent(index).isVisible();
    }

    /**
     * Checks to verify specified index is within bounds
     * @param index position of component in layout
     */
    private void checkBounds(int index) {
        int size = this.size();
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index value of " + index + " is not within bounds of [0, " + (size - 1) + "]");
        }
    }

    private void setVisible(int index) {
        this.checkBounds(index);
        int idx = 0;
        for (Component c : this) {
            if (idx == index) {
                c.setVisible(true);
            } else if (c.isVisible()) {
                c.setVisible(false);
            }
            idx++;
        }
        this.currentCard = index;
    }
}
