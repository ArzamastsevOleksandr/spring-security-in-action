package com.ssia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class HomeController {

    @GetMapping("/")
    String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "hello " + authentication;
    }

    @GetMapping("/2")
    String hello2(Authentication authentication) {
        log.error("Thread: {}", Thread.currentThread().getName());
        return "hello2 " + authentication;
    }

    @Async
    @GetMapping("/3")
    void hello3(Authentication authentication) {
        log.error("Thread: {}", Thread.currentThread().getName());
        log.error("{}", authentication.getName());
    }

    @GetMapping("/npe")
    String hello4() throws ExecutionException, InterruptedException {
        Callable<String> task = () -> "hello " + SecurityContextHolder.getContext().getAuthentication().getName();
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            return executorService.submit(task).get();
        } finally {
            executorService.shutdown();
        }
    }

    @GetMapping("/ok")
    String hello5() throws ExecutionException, InterruptedException {
        Callable<String> task = () -> "hello " + SecurityContextHolder.getContext().getAuthentication().getName();
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            return executorService.submit(new DelegatingSecurityContextCallable<>(task)).get();
        } finally {
            executorService.shutdown();
        }
    }

    @GetMapping("/ok2")
    String hello6() throws ExecutionException, InterruptedException {
        Callable<String> task = () -> "hello " + SecurityContextHolder.getContext().getAuthentication().getName();
        ExecutorService executorService = new DelegatingSecurityContextExecutorService(Executors.newCachedThreadPool());
        try {
            return executorService.submit(task).get();
        } finally {
            executorService.shutdown();
        }
    }

}
