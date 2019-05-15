package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import util.DBUtil;

/**
 * Servlet implementation class DestroyServlet
 */
@WebServlet("/destroy")
public class DestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String _token =(String)request.getParameter("_token");
        if(_token !=null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();

          //EditServletでセッションスコープに登録したタスクidを取得し、該当のタスクを取得
            Task t = em.find(Task.class, request.getSession().getAttribute("task_id"));

            //データベースを更新
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
            em.close();

            //不要になったセッションスコープを削除
            request.removeAttribute("task_id");

            //一覧ページに戻る
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

}
