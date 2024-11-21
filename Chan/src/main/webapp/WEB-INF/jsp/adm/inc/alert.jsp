<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<script>
    var msg = "<c:out value='${msg}'/>";
    var url = "<c:out value='${url}'/>";
    var type = "<c:out value='${type}'/>";
    alert(msg);
    
    if(type == "script") {
    	location.href="javascript:"+url;
    } else if(type == "url") {
    	location.href = url;
    } else {
    	location.href = "/";
    }
    
    
</script>