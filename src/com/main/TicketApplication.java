package com.main;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.passenger.Passenger;
import com.ticket.Ticket;
import com.train.Train;

public class TicketApplication{

	public static void main(String[] args) throws Exception {
		int trainid;
		String tName = null, tsource = null, tdestination = null;
		double tPrice = 0;
		int tcount, m, y, d;
		int count = 0;
		String name, age, gender;
		String temp;
		Ticket t = null;
		Calendar cal = Calendar.getInstance();
		Date dateCompare = new Date();
		Scanner sc = new Scanner(System.in);
		System.out.println("enter train id");
		temp = sc.nextLine();
		trainid = Integer.parseInt(temp);
		RowSetFactory rsf = RowSetProvider.newFactory();
		JdbcRowSet jr = rsf.createJdbcRowSet();
		jr.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		jr.setUsername("hr");
		jr.setPassword("hr");
		jr.setReadOnly(false);

		try {
			jr.setCommand("select * from trains where train_no =" + trainid);
			jr.execute();

			jr.beforeFirst();
			while (jr.next()) {

				trainid = jr.getInt(1);
				tName = jr.getString(2);
				tsource = jr.getString(3);
				tdestination = jr.getString(4);
				tPrice = jr.getInt(5);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("no train found");
		}
		Train train = new Train(trainid, tName, tsource, tdestination, tPrice);

		if (train.getTrainName() != null) {
			System.out.println("Enter month:");
			temp = sc.nextLine();
			m = Integer.parseInt(temp);
			cal.set(Calendar.MONTH, m);
			System.out.println("Enter day:");
			temp = sc.nextLine();
			d = Integer.parseInt(temp);
			cal.set(Calendar.DATE, d);
			System.out.println("Enter year:");
			temp = sc.nextLine();
			y = Integer.parseInt(temp);
			cal.set(Calendar.YEAR, y);
			Date date = cal.getTime();
			if (date.after(dateCompare)) {
				t = new Ticket(date, train);
				System.out.println("Enter Number of Passenger:");
				temp = sc.nextLine();
				try {
					count = Integer.parseInt(temp);
				} catch (Exception e) {
					System.out.println("not a number");
					e.printStackTrace();
				}
				tcount = 0;

				while (tcount < count) {
					System.out.println("---------------------------------------" + '\n');
					System.out.println("Enter Passenger Name");
					name = sc.nextLine();
					System.out.println("---------------------------------------");
					System.out.println("Enter Passenger Age");
					age = sc.nextLine();
					if (age.matches(".*\\d+.*")) {
						System.out.println("---------------------------------------");
						System.out.println("Enter Passenger Gender");
						gender = sc.nextLine().toLowerCase();
					} else {
						System.out.println("not an number");
						break;
					}
					if (gender.equals("m") || gender.equals("f")) {
						System.out.println("---------------------------------------");
						t.addPassenger(name, Integer.parseInt(age), gender.charAt(0));
						tcount++;
					} else {
						System.out.println("gender does not match");
						break;
					}
				}

			} else {
				System.out.println("Not valid Date");
			}

		} else {
			System.out.println("no train found");
		}
		FileOutputStream out = new FileOutputStream(new File("src/" + t.generatePNR() + ".txt"));
		out.write(t.generateTicket().toString().getBytes());
		// System.out.println();

	}

}
