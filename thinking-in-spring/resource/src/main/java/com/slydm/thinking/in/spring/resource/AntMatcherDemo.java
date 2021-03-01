package com.slydm.thinking.in.spring.resource;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

/**
 * {@link org.springframework.util.AntPathMatcher} demo
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 14:42
 */
public class AntMatcherDemo {

  public static void main(String[] args) {

    AntPathMatcher pathMatcher = new AntPathMatcher();

    // test exact matching
    Assert.isTrue(pathMatcher.match("test", "test"), "");
    Assert.isTrue(pathMatcher.match("/test", "/test"), "");
    Assert.isTrue(pathMatcher.match("http://example.org", "http://example.org"), "");
    Assert.isTrue(!pathMatcher.match("/test.jpg", "test.jpg"), "");
    Assert.isTrue(!pathMatcher.match("test", "/test"), "");
    Assert.isTrue(!pathMatcher.match("/test", "test"), "");

    // test matching with ?'s
    Assert.isTrue(pathMatcher.match("t?st", "test"), "");
    Assert.isTrue(pathMatcher.match("??st", "test"), "");
    Assert.isTrue(pathMatcher.match("tes?", "test"), "");
    Assert.isTrue(pathMatcher.match("te??", "test"), "");
    Assert.isTrue(pathMatcher.match("?es?", "test"), "");
    Assert.isTrue(!pathMatcher.match("tes?", "tes"), "");
    Assert.isTrue(!pathMatcher.match("tes?", "testt"), "");
    Assert.isTrue(!pathMatcher.match("tes?", "tsst"), "");

    // test matching with *'s
    Assert.isTrue(pathMatcher.match("*", "test"), "");
    Assert.isTrue(pathMatcher.match("test*", "test"), "");
    Assert.isTrue(pathMatcher.match("test*", "testTest"), "");
    Assert.isTrue(pathMatcher.match("test/*", "test/Test"), "");
    Assert.isTrue(pathMatcher.match("test/*", "test/t"), "");
    Assert.isTrue(pathMatcher.match("test/*", "test/"), "");
    Assert.isTrue(pathMatcher.match("*test*", "AnothertestTest"), "");
    Assert.isTrue(pathMatcher.match("*test", "Anothertest"), "");
    Assert.isTrue(pathMatcher.match("*.*", "test."), "");
    Assert.isTrue(pathMatcher.match("*.*", "test.test"), "");
    Assert.isTrue(pathMatcher.match("*.*", "test.test.test"), "");
    Assert.isTrue(pathMatcher.match("test*aaa", "testblaaaa"), "");
    Assert.isTrue(!pathMatcher.match("test*", "tst"), "");
    Assert.isTrue(!pathMatcher.match("test*", "tsttest"), "");
    Assert.isTrue(!pathMatcher.match("test*", "test/"), "");
    Assert.isTrue(!pathMatcher.match("test*", "test/t"), "");
    Assert.isTrue(!pathMatcher.match("test/*", "test"), "");
    Assert.isTrue(!pathMatcher.match("*test*", "tsttst"), "");
    Assert.isTrue(!pathMatcher.match("*test", "tsttst"), "");
    Assert.isTrue(!pathMatcher.match("*.*", "tsttst"), "");
    Assert.isTrue(!pathMatcher.match("test*aaa", "test"), "");
    Assert.isTrue(!pathMatcher.match("test*aaa", "testblaaab"), "");

    // test matching with ?'s and /'s
    Assert.isTrue(pathMatcher.match("/?", "/a"), "");
    Assert.isTrue(pathMatcher.match("/?/a", "/a/a"), "");
    Assert.isTrue(pathMatcher.match("/a/?", "/a/b"), "");
    Assert.isTrue(pathMatcher.match("/??/a", "/aa/a"), "");
    Assert.isTrue(pathMatcher.match("/a/??", "/a/bb"), "");
    Assert.isTrue(pathMatcher.match("/?", "/a"), "");

    // test matching with **'s
    Assert.isTrue(pathMatcher.match("/**", "/testing/testing"), "");
    Assert.isTrue(pathMatcher.match("/*/**", "/testing/testing"), "");
    Assert.isTrue(pathMatcher.match("/**/*", "/testing/testing"), "");
    Assert.isTrue(pathMatcher.match("/bla/**/bla", "/bla/testing/testing/bla"), "");
    Assert.isTrue(pathMatcher.match("/bla/**/bla", "/bla/testing/testing/bla/bla"), "");
    Assert.isTrue(pathMatcher.match("/**/test", "/bla/bla/test"), "");
    Assert.isTrue(pathMatcher.match("/bla/**/**/bla", "/bla/bla/bla/bla/bla/bla"), "");
    Assert.isTrue(pathMatcher.match("/bla*bla/test", "/blaXXXbla/test"), "");
    Assert.isTrue(pathMatcher.match("/*bla/test", "/XXXbla/test"), "");
    Assert.isTrue(!pathMatcher.match("/bla*bla/test", "/blaXXXbl/test"), "");
    Assert.isTrue(!pathMatcher.match("/*bla/test", "XXXblab/test"), "");
    Assert.isTrue(!pathMatcher.match("/*bla/test", "XXXbl/test"), "");

    Assert.isTrue(!pathMatcher.match("/????", "/bala/bla"), "");
    Assert.isTrue(!pathMatcher.match("/**/*bla", "/bla/bla/bla/bbb"), "");

    Assert.isTrue(
        pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing/"),
        "");
    Assert.isTrue(pathMatcher.match("/*bla*/**/bla/*", "/XXXblaXXXX/testing/testing/bla/testing"),
        "");
    Assert.isTrue(
        pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing"),
        "");
    Assert.isTrue(pathMatcher
        .match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing.jpg"), "");

    Assert.isTrue(
        pathMatcher.match("*bla*/**/bla/**", "XXXblaXXXX/testing/testing/bla/testing/testing/"),
        "");
    Assert
        .isTrue(pathMatcher.match("*bla*/**/bla/*", "XXXblaXXXX/testing/testing/bla/testing"), "");
    Assert.isTrue(
        pathMatcher.match("*bla*/**/bla/**", "XXXblaXXXX/testing/testing/bla/testing/testing"), "");
    Assert.isTrue(
        !pathMatcher.match("*bla*/**/bla/*", "XXXblaXXXX/testing/testing/bla/testing/testing"), "");

    Assert.isTrue(!pathMatcher.match("/x/x/**/bla", "/x/x/x/"), "");

    Assert.isTrue(pathMatcher.match("/foo/bar/**", "/foo/bar"), "");

    Assert.isTrue(pathMatcher.match("", ""), "");

    Assert.isTrue(pathMatcher.match("/{bla}.*", "/testing.html"), "");


  }


}
