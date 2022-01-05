package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
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
            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member1 member = new Member1();
            member.setUsername("member1");
            member.setTeam(team); // 단방향 연관관계 설정, 참조 저장
            em.persist(member);

            em.flush();
            em.clear();

            Member1 findMember = em.find(Member1.class, member.getId());

            Team findTeam = findMember.getTeam();
            System.out.println("findTeam.getName() = " + findTeam.getName());

            List<Member1> members = findMember.getTeam().getMembers();

            for (Member1 m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
