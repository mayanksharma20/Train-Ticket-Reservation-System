package com.mayank.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mayank.beans.TrainBean;
import com.mayank.beans.TrainException;
import com.mayank.constant.UserRole;
import com.mayank.service.TrainService;
import com.mayank.service.impl.TrainServiceImpl;
import com.mayank.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/useravail")
public class UserAvailServlet extends HttpServlet {
	private TrainService trainService = new TrainServiceImpl();

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();

		TrainUtil.validateUserAuthorization(req, UserRole.CUSTOMER);

		try {
				// Retrieve the train number from the form input
			String trainNo = req.getParameter("trainno");
				// Get train details based on train number
			TrainBean train = trainService.getTrainById(trainNo);
			if (train != null) {
				RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
				rd.include(req, res);
				pw.println(
						"<div class='tab'>" + "		<p1 class='menu'>" + "	Hello " + TrainUtil.getCurrentUserName(req)
								+ " ! Welcome to our Train Reservation System" + "		</p1>" + "	</div>");
				pw.println("<div class='main'><p1 class='menu'>Available Seats are <p2 class=\"red\"> "
						+ train.getSeats() + " Seats</p2></p1></div>");
				pw.println("<div class='tab'>" + "<table>" + "<tr><td class='blue'>Train Name :</td><td>"
						+ train.getTr_name() + "</td></tr>" + "<tr><td class='blue'>Train Number :</td><td>"
						+ train.getTr_no() + "</td></tr>" + "<tr><td class='blue'>From Station :</td><td>"
						+ train.getFrom_stn() + "</td></tr>" + "<tr><td class='blue'>To Station :</td><td>"
						+ train.getTo_stn() + "</td></tr>" + "<tr><td class='blue'>Available Seats:</td><td>"
						+ train.getSeats() + "</td></tr>" + "<tr><td class='blue'>Fare (INR) :</td><td>"
						+ train.getFare() + " RS</td></tr>" + "</table>" + "</div>");
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("Availability.html");
				rd.include(req, res);

				pw.println("<div class='tab'><p1 class='menu'>Train No." + trainNo + " is Not Available !</p1></div>");
			}
		} catch (Exception e) {
			throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());
		}

	}

}
