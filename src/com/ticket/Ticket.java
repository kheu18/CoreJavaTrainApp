package com.ticket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.passenger.Passenger;
import com.train.Train;

public class Ticket {
	static int counter = 100;
	Date travelDate;
	Train train;
	TreeMap<Passenger, Integer> passenger = new TreeMap<>();

	public Ticket(Date travelDate, Train train) {
		super();
		this.travelDate = travelDate;
		this.train = train;
	}

	public String generatePNR() {
		counter = counter++;
		SimpleDateFormat sdFormat = new SimpleDateFormat("MMddyyyy");
		String stDate = sdFormat.format(travelDate);
		String PNR = String.valueOf(train.getSource().charAt(0)) + String.valueOf(train.getDestination().charAt(0))
				+ "_" + stDate + "_" + String.valueOf(counter);
		return PNR;
	}

	public double calcPassengerFare(Passenger p) {
		double to = train.getTicketPrice();
		double tm;
		if (p.getGender() == 'f' && p.getAge() <= 12) {
			tm = (to * .25);
			tm = (tm * .50);
			return tm;
		} else if (p.getGender() == 'f' && p.getAge() >= 60) {
			tm = (to * .25);
			tm = (tm * .60);
			return tm;
		} else if (p.getGender() == 'f') {
			tm = to * .25;
			return tm;
		} else if (p.getAge() <= 12 && p.getGender() == 'm') {
			tm = to * .50;
			return tm;
		} else if (p.getAge() >= 60 && p.getGender() == 'm') {
			tm = to * .60;
			return tm;
		} else {
			return train.getTicketPrice();
		}
	}

	public void addPassenger(String s, int i, char c) {
		Passenger p = new Passenger(s, i, c);
		Ticket t = new Ticket(travelDate, train);
		passenger.put(p, (int) t.calcPassengerFare(p));
	}

	public double calculateTotalTicketPrice() {
		double a = 0;

		for (Map.Entry<Passenger, Integer> e : passenger.entrySet()) {
			a = a + e.getValue().doubleValue();
		}
		System.out.println();
		return a;
	}

	public StringBuilder generateTicket() {
		StringBuilder sb = new StringBuilder();
		

		sb.append("PNR             : " + generatePNR().toString() + "\n");
		sb.append("Train no        : " + train.getTrainNo() + "\n");
		sb.append("Train Name	: " + train.getTrainName() + "\n");
		sb.append("From      	: " + train.getSource() + "\n");
		sb.append("To	        : " + train.getDestination() + "\n");
		sb.append("Travel Date	: " + travelDate + "\n");
		sb.append("\n");
		sb.append("Passenger:");
		sb.append("\n");
		sb.append("Name			" + "Age			" + "Gender			" + "Fare			");
		sb.append("\n");
		for (Map.Entry<Passenger, Integer> e : passenger.entrySet()) {
			sb.append(e.getKey().getName() + "			" + e.getKey().getAge() + "			 " + e.getKey().getGender()
					+ "			" + e.getValue());
			sb.append("\n");

		}
		sb.append("\n");
		sb.append("Total Price : " + String.valueOf(calculateTotalTicketPrice()));
		return sb;
	}

	public void writeTicket() {

	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public TreeMap<Passenger, Integer> getPassenger() {
		return passenger;
	}

	public void setPassenger(TreeMap<Passenger, Integer> passenger) {
		this.passenger = passenger;
	}

}
