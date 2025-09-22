// 代码生成时间: 2025-09-23 07:01:57
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service to handle sorting operations.
# FIXME: 处理边界情况
 */
# 添加错误处理
@Service
public class SortingService {

    /**
     * Sorts a list of numbers using the quicksort algorithm.
     *
     * @param numbers List of numbers to be sorted.
     * @return A sorted list of numbers.
     */
    public List<Integer> quickSort(List<Integer> numbers) {
# 增强安全性
        if (numbers == null || numbers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "List of numbers is empty or null");
        }
# 扩展功能模块
        return quickSortHelper(numbers, 0, numbers.size() - 1);
    }

    private List<Integer> quickSortHelper(List<Integer> numbers, int left, int right) {
        if (left >= right) {
            return numbers;
        }
# NOTE: 重要实现细节

        int pivotIndex = partition(numbers, left, right);
        return mergeAndSort(quickSortHelper(numbers, left, pivotIndex - 1), quickSortHelper(numbers, pivotIndex + 1, right));
    }

    private int partition(List<Integer> numbers, int left, int right) {
        int pivot = numbers.get(right);
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (numbers.get(j) < pivot) {
                i++;
# 添加错误处理
                Collections.swap(numbers, i, j);
            }
        }
        Collections.swap(numbers, i + 1, right);
# 增强安全性
        return i + 1;
    }
# 扩展功能模块

    private List<Integer> mergeAndSort(List<Integer> left, List<Integer> right) {
        return Arrays.stream(new Integer[left.size() + right.size()])
                .mapToObj(i -> i < left.size() ? left.get(i) : right.get(i - left.size()))
                .sorted()
# 优化算法效率
                .collect(Collectors.toList());
    }
}
# 增强安全性
