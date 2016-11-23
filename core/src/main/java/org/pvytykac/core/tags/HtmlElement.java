package org.pvytykac.core.tags;

/**
 * @author paly
 * @since 16. 11. 2016
 */
interface HtmlElement {

    /**
     * @return opening html tags of the element and its children
     * @throws IllegalStateException if there is at least one child element in the body chain with more then 2 children
     */
    String open();

    /**
     * @return closing html tags of the element and its children
     * @throws IllegalStateException if there is at least one child element in the body chain with more then 2 children
     */
    String close();

    /**
     * @return full html of the element including its body contents
     */
    String full();

}
