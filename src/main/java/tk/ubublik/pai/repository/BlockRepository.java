package tk.ubublik.pai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tk.ubublik.pai.entity.Block;
import tk.ubublik.pai.entity.User;

import java.util.Date;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long>, JpaSpecificationExecutor<Block> {

	@Query("select case when count(b) > 0 then true else false end " +
			"from Block b where b.user = :user " +
			"and b.start <= :date " +
			"and b.end <= :date")
	boolean isUserBlocked(@Param("user") User user, @Param("date") Date date);

	@Query("select b from Block b join fetch b.user join fetch b.admin where b.id = :id")
	Block getFetched(@Param("id") long id);
}
