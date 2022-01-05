package jpabook.jpashop;

import jpabook.jpashop.section5.Member1;
import jpabook.jpashop.section5.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 저장1 (실패)
/*
            Member1 member = new Member1();
            member.setUsername("member1");
            em.persist(member);

            Team team = new Team();
            team.setName("TeamA");
            
            // member가 연관관계의 주인이기 때문에 저장이 안된다.
            // 역방향(주인이 아닌 방향)만 연관관계 설정
            team.getMembers().add(member); 
            em.persist(team);
*/

            // 저장2 (성공)
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member1 member = new Member1();
            member.setUsername("member1");
            member.changeTeam(team); // 연관관계 편의 메소드
            em.persist(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member1> members = findTeam.getMembers();

            System.out.println("===============");
            for (Member1 m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("===============");

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
