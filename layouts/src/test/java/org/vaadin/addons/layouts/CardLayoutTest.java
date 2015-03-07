
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

import com.vaadin.ui.Label;

import junit.framework.Assert;

import org.junit.Test;

public class CardLayoutTest {

    @Test
    public void testFirstComponentIsVisible() {
        CardLayout layout = new CardLayout();
        Assert.assertTrue(layout.isEmpty());
        Assert.assertEquals(0, layout.size());

        Label label = new Label("foo");
        label.setVisible(false);
        layout.addComponent(label);

        Assert.assertEquals(1, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        Assert.assertEquals(label, layout.getVisibleComponent());
        Assert.assertTrue(label.isVisible());
    }

    @Test
    public void testFirstComponentIsVisibleAfterAddition() {
        CardLayout layout = new CardLayout();

        Label label = new Label("foo");
        label.setVisible(false);
        layout.addComponent(label);
        layout.addComponent(new Label("bar"));

        Assert.assertEquals(2, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        Assert.assertEquals(label, layout.getVisibleComponent());
        Assert.assertTrue(label.isVisible());
    }

    @Test
    public void testAddComponentToFrontByIndex() {
        CardLayout layout = new CardLayout();

        Label label = new Label("foo");
        label.setVisible(false);
        layout.addComponent(label);

        Label label2 = new Label("bar");
        layout.addComponent(label2, 0);

        Assert.assertEquals(2, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        Assert.assertEquals(label2, layout.getVisibleComponent());
        Assert.assertTrue(label2.isVisible());
        Assert.assertFalse(label.isVisible());
    }

    @Test
    public void testAddComponentAsFirst() {
        CardLayout layout = new CardLayout();

        Label label = new Label("foo");
        label.setVisible(false);
        layout.addComponent(label);

        Label label2 = new Label("bar");
        layout.addComponentAsFirst(label2);

        Assert.assertEquals(2, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        Assert.assertEquals(label2, layout.getVisibleComponent());
        Assert.assertTrue(label2.isVisible());
        Assert.assertFalse(label.isVisible());
    }

    @Test
    public void testNext() {
        CardLayout layout = new CardLayout();

        layout.addComponent(new Label("foo"));
        layout.addComponent(new Label("bar"));
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.next();
        Assert.assertEquals(1, layout.getVisibleComponentIndex());
        Assert.assertEquals("bar", ((Label)layout.getVisibleComponent()).getValue());
        layout.next();
        Assert.assertEquals(2, layout.getVisibleComponentIndex());
        Assert.assertEquals("baz", ((Label)layout.getVisibleComponent()).getValue());
        layout.next();
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        Assert.assertEquals("foo", ((Label)layout.getVisibleComponent()).getValue());
    }

    @Test
    public void testPrevious() {
        CardLayout layout = new CardLayout();

        layout.addComponent(new Label("foo"));
        layout.addComponent(new Label("bar"));
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.previous();
        Assert.assertEquals(2, layout.getVisibleComponentIndex());
        Assert.assertEquals("baz", ((Label)layout.getVisibleComponent()).getValue());
        layout.previous();
        Assert.assertEquals(1, layout.getVisibleComponentIndex());
        Assert.assertEquals("bar", ((Label)layout.getVisibleComponent()).getValue());
        layout.previous();
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        Assert.assertEquals("foo", ((Label)layout.getVisibleComponent()).getValue());
    }

    @Test
    public void testFirstLast() {
        CardLayout layout = new CardLayout();

        layout.addComponent(new Label("foo"));
        layout.addComponent(new Label("bar"));
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.last();
        Assert.assertEquals(2, layout.getVisibleComponentIndex());
        Assert.assertEquals("baz", ((Label)layout.getVisibleComponent()).getValue());
        layout.first();
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        Assert.assertEquals("foo", ((Label)layout.getVisibleComponent()).getValue());
    }

    @Test
    public void testShowByIndex() {
        CardLayout layout = new CardLayout();

        layout.addComponent(new Label("foo"));
        layout.addComponent(new Label("bar"));
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.show(1);
        Assert.assertEquals(1, layout.getVisibleComponentIndex());
        Assert.assertEquals("bar", ((Label)layout.getVisibleComponent()).getValue());

        try {
            layout.show(100);
            Assert.fail("Exceptions should have been thrown");
        } catch (IndexOutOfBoundsException e) {
            // ignore
        }
    }

    @Test
    public void testShowByComponent() {
        CardLayout layout = new CardLayout();

        Label label = new Label("bar");
        layout.addComponent(new Label("foo"));
        layout.addComponent(label);
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.show(null);
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.show(label);
        Assert.assertEquals(1, layout.getVisibleComponentIndex());
        Assert.assertEquals("bar", ((Label)layout.getVisibleComponent()).getValue());
    }

    @Test
    public void testRemoveNonVisible() {
        CardLayout layout = new CardLayout();

        Label label = new Label("bar");
        layout.addComponent(new Label("foo"));
        layout.addComponent(label);
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.removeComponent(label);
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
    }

    @Test
    public void testRemoveVisible() {
        CardLayout layout = new CardLayout();

        Label label = new Label("bar");
        layout.addComponent(new Label("foo"));
        layout.addComponent(label);
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.show(label);
        Assert.assertEquals(1, layout.getVisibleComponentIndex());
        layout.removeComponent(label);
        Assert.assertEquals(1, layout.getVisibleComponentIndex());
        Assert.assertEquals("baz", ((Label)layout.getVisibleComponent()).getValue());
    }

    @Test
    public void testReplaceNonVisible() {
        CardLayout layout = new CardLayout();

        Label label = new Label("bar");
        Label label2 = new Label("baz");
        layout.addComponent(new Label("foo"));
        layout.addComponent(label);
        layout.addComponent(label2);

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.replaceComponent(label, label2);
        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
    }

    @Test
    public void testReplaceVisible() {
        CardLayout layout = new CardLayout();

        Label label = new Label("foo");
        Label label2 = new Label("bar");
        layout.addComponent(label);
        layout.addComponent(label2);
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.replaceComponent(label, label2);
        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        Assert.assertEquals("bar", ((Label)layout.getVisibleComponent()).getValue());
    }

    @Test
    public void testReplaceVisibleWithNew() {
        CardLayout layout = new CardLayout();

        Label label = new Label("foo");
        layout.addComponent(label);
        layout.addComponent(new Label("bar"));
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.replaceComponent(label, new Label("goo"));
        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        Assert.assertEquals("goo", ((Label)layout.getVisibleComponent()).getValue());
    }

    @Test
    public void testRemoveAll() {
        CardLayout layout = new CardLayout();

        layout.addComponent(new Label("foo"));
        layout.addComponent(new Label("bar"));
        layout.addComponent(new Label("baz"));

        Assert.assertEquals(3, layout.size());
        Assert.assertEquals(0, layout.getVisibleComponentIndex());
        layout.removeAllComponents();
        Assert.assertEquals(0, layout.size());
        Assert.assertEquals(-1, layout.getVisibleComponentIndex());
    }
}
