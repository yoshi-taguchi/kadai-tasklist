package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import util.DBUtil;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        //show.jspから"${pageContext.request.contextPath }/edit?id=${task.id}"によって送信されたidのメッセージを取得
        Task t = em.find(Task.class,Integer.parseInt(request.getParameter("id")));

        em.close();

        //メッセージ情報とセッションIDをリクエストスコープに登録
        request.setAttribute("task", t);
        request.setAttribute("_token",request.getSession().getId());

        // edit・destroy用にメッセージIDをセッションスコープに登録。※編集処理後削除すること。
        request.getSession().setAttribute("task_id", t.getId());


        RequestDispatcher rd =request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
        rd.forward(request,response);
    }

}
