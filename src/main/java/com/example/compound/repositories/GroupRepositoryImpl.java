//package com.example.compound.repositories;
//
//import com.example.compound.entities.Group;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class GroupRepositoryImpl implements GroupRepository {
//
////    @Autowired
////    private JdbcTemplate jdbcTemplate;
//
////    private static final String SQL_CREATE_GROUP = "INSERT INTO groups VALUES(10, ?, ?, '{}', '{}', ?)";
//
////    @Override
////    public Integer addGroupToDatabase(String name, String description, List<String> members) {
////        try {
////            KeyHolder keyHolder = new GeneratedKeyHolder();
////            jdbcTemplate.update(connection -> {
////                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_GROUP, Statement.RETURN_GENERATED_KEYS);
////                ps.setString(1, name);
////                ps.setString(2, "'{1, 2}'");
////                ps.setString(3, description);
////                return ps;
////            }, keyHolder);
////            return (Integer) keyHolder.getKeys().get("guid");
//////            jdbcTemplate.update(SQL_CREATE_GROUP, name, members, description);
//////            return 1;
////        } catch (Exception ignored) {
////        }
////        return 0;
////    }
//
////    @Override
////    public boolean getGroupInfo(String guid) {
////        return false;
////    }
//
//
//    @Override
//    public List<Group> findAll() {
//        return null;
//    }
//
//    @Override
//    public List<Group> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<Group> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<Group> findAllById(Iterable<Integer> integers) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(Integer integer) {
//
//    }
//
//    @Override
//    public void delete(Group entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Integer> integers) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Group> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public <S extends Group> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Group> List<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<Group> findById(Integer integer) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(Integer integer) {
//        return false;
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends Group> S saveAndFlush(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Group> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<Group> entities) {
//
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public Group getOne(Integer integer) {
//        return null;
//    }
//
//    @Override
//    public Group getById(Integer integer) {
//        return null;
//    }
//
//    @Override
//    public <S extends Group> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Group> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    @Override
//    public <S extends Group> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public <S extends Group> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Group> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Group> boolean exists(Example<S> example) {
//        return false;
//    }
//}
