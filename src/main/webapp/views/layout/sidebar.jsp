<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="flex-w flex-sb-m p-b-52">
    <div class="flex-w flex-l-m filter-tope-group m-tb-10">
        <a href="home" class="stext-106 cl6 hov1 bor3 trans-04 m-r-32 m-tb-5 ${tag == null ? 'how-active1' : ''}">
            Tất cả
        </a>

        <c:forEach items="${listCategory}" var="c">
            <a href="category?cid=${c.id}" class="stext-106 cl6 hov1 bor3 trans-04 m-r-32 m-tb-5 ${tag == String.valueOf(c.id) ? 'how-active1' : ''}">
                ${c.name}
            </a>
        </c:forEach>
    </div>

    <div class="flex-w flex-c-m m-tb-10">
        <div class="flex-c-m stext-106 cl6 size-104 bor4 pointer hov-btn3 trans-04 m-r-8 m-tb-4 js-show-filter">
            <i class="icon-filter cl2 m-r-6 fs-15 trans-04 zmdi zmdi-filter-list"></i>
            <i class="icon-close-filter cl2 m-r-6 fs-15 trans-04 zmdi zmdi-close dis-none"></i>
             Filter
        </div>

        <div class="flex-c-m stext-106 cl6 size-105 bor4 pointer hov-btn3 trans-04 m-tb-4 js-show-search">
            <i class="icon-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-search"></i>
            <i class="icon-close-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-close dis-none"></i>
            Search
        </div>
    </div>
    
    <div class="dis-none panel-search w-full p-t-10 p-b-15">
        <div class="bor8 dis-flex p-l-15">
            <button class="size-113 flex-c-m fs-16 cl2 hov-cl1 trans-04">
                <i class="zmdi zmdi-search"></i>
            </button>
            <input class="mtext-107 cl2 size-114 plh2 p-r-15" type="text" name="search-product" placeholder="Search">
        </div>  
    </div>

    <div class="dis-none panel-filter w-full p-t-10">
        <div class="wrap-filter flex-w bg6 w-full p-lr-40 p-t-27 p-lr-15-sm">
            <div class="filter-col1 p-r-15 p-b-27">
                <div class="mtext-102 cl2 p-b-15">Sắp xếp</div>
                <ul>
                    <li class="p-b-6"><a href="#" class="filter-link stext-106 trans-04">Mặc định</a></li>
                    <li class="p-b-6"><a href="#" class="filter-link stext-106 trans-04">Mới nhất</a></li>
                </ul>
            </div>
            <div class="filter-col2 p-r-15 p-b-27">
                <div class="mtext-102 cl2 p-b-15">Giá cả</div>
                <ul>
                    <li class="p-b-6"><a href="#" class="filter-link stext-106 trans-04 filter-link-active">All</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>