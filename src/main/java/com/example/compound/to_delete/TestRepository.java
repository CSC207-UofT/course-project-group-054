package com.example.compound.to_delete;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TestRepository implements TestRepo {

    @Override
    public List<Test> findAll() {
        return null;
    }

    @Override
    public List<Test> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Test> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Test> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Test entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Test> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Test> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Test> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Test> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Test> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Test> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Test> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Test getOne(Integer integer) {
        return null;
    }

    @Override
    public Test getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Test> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Test> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Test> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Test> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Test> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Test> boolean exists(Example<S> example) {
        return false;
    }
}
