//package com.meidi.repository;
//
//import com.meidi.domain.MdProject;
//import com.meidi.repository.custom.ProjectListRepositoryCustom;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.PagingAndSortingRepository;
//
//import java.util.List;
//
///**
// * Created by luanpeng on 16/3/24.
// */
//public interface ProjectRepository extends PagingAndSortingRepository<MdProject, Integer> {
//
//    /**
//     * 同城市下的一上线的不同项目
//     * @param cityId
//     * @param state
//     * @param projectId
//     * @param pageable
//     * @return
//     */
//    List<MdProject> findByDicCity_IdAndStateAndIdIsNot(int cityId,int state,int projectId, Pageable pageable);
//}
