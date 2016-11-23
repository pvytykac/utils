package org.pvytykac.paging;

import org.pvytykac.core.url.URL;

/**
 * @author paly
 * @since 18. 11. 2016
 */
public interface PagingSupport {

    int DEFAULT_PAGE_SIZE = 50;
    int[] PAGE_SIZES = new int[]{10, 25, 50, 100, 500, 1000};

    // names of http parameters used for paging
    String PAGE_PARAM = "page";
    String OFFSET_ID_PARAM = "offsetId";
    String NEXT_PARAM = "next";
    String PREV_PARAM = "prev";
    String OFFSET_PARAM = "offset";
    String INCLUSIVE_PARAM = "inclusive";

    /**
     * @return one-based index of the current page
     */
    int getCurrentPage();

    /**
     * @return total amount of items being paged
     */
    int getTotalSize();

    /**
     * @return max amount of items displayed on a single page
     */
    int getPageSize();

    /**
     * @return offsetId used to produce next pages (usually id of the last element on the current page)
     */
    String getNextOffsetId();

    /**
     * @return offsetId used to produce previous pages (usually id of the first element on the current page)
     */
    String getPrevOffsetId();

    /**
     * @return total amount of pages required to display all the items
     */
    int getTotalPages();

    /**
     * @return true if there are more pages after the current one, false otherwise
     */
    boolean hasNextPage();

    /**
     * @return true if there are more pages before the current one, false otherwise
     */
    boolean hasPreviousPage();

    /**
     * @return true if current page is the last one, false otherwise
     */
    boolean isLastPage();

    /**
     * @return true if current page is the first one, false otherwise
     */
    boolean isFirstPage();

    /**
     * @return amount of items displayed on previous pages (excluding the current page)
     */
    int getItemsOnPreviousPages();

    /**
     * @return amount of items displayed on next pages (excluding the current page)
     */
    int getItemsOnNextPages();

    /**
     * @return one-based index representing the position of the first item, on the current page, in the item list
     */
    int getViewRangeFrom();

    /**
     * @return one-based index representing the position of the last item, on the current page, in the item list
     */
    int getViewRangeTo();

    /**
     * @param page one-based page index
     * @return true if a page with the provided index exists
     */
    boolean pageExists(int page);

    /**
     * @return URL pointing to the next page or NULL if the page does not exist
     */
    URL getNextPageURL();

    /**
     * @return URL pointing to the previous page or NULL if the page does not exist
     */
    URL getPreviousPageURL();

    /**
     * @return URL pointing to the first page
     */
    URL getFirstPageURL();

    /**
     * @return URL pointing to the last page
     */
    URL getLastPageURL();

    /**
     * @param page one-based page index
     * @return URL pointing to the requested page or NULL if the page does not exist
     */
    URL getPageURL(int page);

    /**
     * @param newPageSize the new page size to use for resizing the pages
     * @return URL pointing to the current page after its re-sized
     */
    URL getResizeURL(int newPageSize);

}
