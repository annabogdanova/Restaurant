package com.epam.restaurant.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.restaurant.shared.Bill;
import com.epam.restaurant.shared.Dish;
import com.epam.restaurant.shared.Order;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Restaurant implements EntryPoint {
	static Logger logger = Logger.getLogger("");
	
	// The message displayed to the user when the server cannot be reached or returns an error.
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	// Create a remote service proxy to talk to the server-side Restaurant service.
	private final RestaurantServiceAsync restaurantService = GWT.create(RestaurantService.class);
	
	private String clientName;
	
	//The entry point method.
	public void onModuleLoad() {
	
		final Button menuButton = new Button("choose dishes");
		final Button payButton = new Button("pay for order");
		final Button adminButton = new Button("log in");

		menuButton.addStyleName("menuButton");
		payButton.addStyleName("payButton");
		adminButton.addStyleName("adminButton");

		RootPanel.get("adminButton").add(adminButton);

		menuButton.setEnabled(false);
		payButton.setEnabled(false);
		
		// Create the popup dialog box
		final DialogBox loginBox = new DialogBox();
		loginBox.setText("Log in as admin");
		loginBox.setAnimationEnabled(true);
		final TextBox nameField = new TextBox();
		nameField.setText("admin");
		final Button loginButton = new Button("Log in");
		final Button closeButton = new Button("Close");
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(nameField);
		dialogVPanel.add(loginButton);
		dialogVPanel.add(closeButton);
		loginBox.setWidget(dialogVPanel);

		// Create the popup dialog box
		final DialogBox errorBox = new DialogBox();
		errorBox.setText("Log in as admin");
		errorBox.setAnimationEnabled(true);
		final Button closeErrorButton = new Button("Close");
		closeButton.getElement().setId("closeButton");
		VerticalPanel errorVPanel = new VerticalPanel();
		errorVPanel.addStyleName("dialogVPanel");
		errorVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		errorVPanel.add(closeErrorButton);
		errorBox.setWidget(errorVPanel);
		
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loginBox.hide();
				adminButton.setEnabled(true);
			}
		});
		closeErrorButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				errorBox.hide();
				adminButton.setEnabled(true);
			}
		});

		adminButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				adminButton.setEnabled(false);
				logger.log(Level.SEVERE, "ADMIN clear container");
				RootPanel.get("ConfirmContainer").clear();
				loginBox.center();
			}
		});
		
		
		ClickHandler loginHandler = new ClickHandler() {
			public void onClick(ClickEvent event) {
				logger.log(Level.SEVERE, "ADMIN try admin");
				if (nameField.getValue().equals("admin")) {
					logger.log(Level.SEVERE, "ADMIN it's admin");
					restaurantService.getOrders(clientName,						
						new AsyncCallback<List<Order>>() {
							public void onFailure(Throwable caught) {
								logger.log(Level.SEVERE, "ADMIN getOrders error");
								errorBox.setText("ADMIN An error occured in getOrders");
								errorBox.center();
								closeErrorButton.setFocus(true);
							}
							public void onSuccess(final List<Order> orders) {
								logger.log(Level.SEVERE, " ADMIN getOrders have result, hide login ");
								loginBox.hide();
								
								int amount = 0;
								for (Order order : orders) {
									amount += Integer.parseInt(order.getPrice());
									logger.log(Level.SEVERE, order.getDish());
									RootPanel.get("ConfirmContainer").add(new Label(order.getDish() + ", " + order.getPrice()));
								}
								RootPanel.get("ConfirmContainer").add(new Label("Total amount: " + amount));										
								final Button confirmButton = new Button("confirm an order");
								confirmButton.addStyleName("confirmButton");
								confirmButton.setFocus(true);
								RootPanel.get("ConfirmContainer").add(confirmButton);
								
								logger.log(Level.SEVERE, "ADMIN show confirm container");
								RootPanel.get("ConfirmContainer").setVisible(true);
								RootPanel.get("MenuContainer").setVisible(false);
								RootPanel.get("OrderContainer").setVisible(false);
								final int total = amount;
								confirmButton.addClickHandler(new ClickHandler() {
									public void onClick(ClickEvent event) {
										restaurantService.confirmOrder(clientName, total,
											new AsyncCallback<Void>() {
												public void onFailure(Throwable caught) {
													errorBox.setText("Confirm Order Procedure Call - Failure");
													errorBox.center();
													closeButton.setFocus(true);
												}
												public void onSuccess(Void result) {
													logger.log(Level.SEVERE, "ADMIN Order confirmed");
													RootPanel.get("ConfirmContainer").setVisible(false);
													adminButton.setEnabled(true);
												}
											});
									}
								});
							};									
						});
				}
				else {
					clientName = nameField.getValue();
					logger.log(Level.SEVERE, "getMenu client " + clientName);
					loginBox.hide();
					RootPanel.get("menuButton").add(menuButton);
					RootPanel.get("payButton").add(payButton);
					menuButton.setEnabled(true);
					payButton.setEnabled(true);
				}
			}
		};
		loginButton.addClickHandler(loginHandler);
		
		
		
		menuButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				adminButton.setEnabled(true);
				RootPanel.get("MenuContainer").clear();
				restaurantService.getMenu(						
					new AsyncCallback<List<Dish>>() {
						public void onFailure(Throwable caught) {
							errorBox.setText("An error occured");
							errorBox.center();
							closeErrorButton.setFocus(true);
							closeErrorButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									errorBox.hide();
								}
							});
						}
						public void onSuccess(final List<Dish> menu) {
							logger.log(Level.SEVERE, "getMenu have result");
							List<String> menuList = new ArrayList<String> (menu.size());
							for (Dish dish : menu) {
								menuList.add(dish.getDish() + ", " + dish.getPrice());
								logger.log(Level.SEVERE, dish.getDish());
							}
							final CheckBox cb1 = new CheckBox(menuList.get(0));
							final CheckBox cb2 = new CheckBox(menuList.get(1));
							final CheckBox cb3 = new CheckBox(menuList.get(2));
							final CheckBox cb4 = new CheckBox(menuList.get(3));
							final CheckBox cb5 = new CheckBox(menuList.get(4));
							RootPanel.get("MenuContainer").add(cb1);
							RootPanel.get("MenuContainer").add(cb2);
							RootPanel.get("MenuContainer").add(cb3);
							RootPanel.get("MenuContainer").add(cb4);
							RootPanel.get("MenuContainer").add(cb5);
							
							final Button chooseButton = new Button("make an order");
							chooseButton.addStyleName("chooseButton");
							chooseButton.setFocus(true);
							RootPanel.get("MenuContainer").add(chooseButton);
							
							RootPanel.get("MenuContainer").setVisible(true);
							RootPanel.get("ConfirmContainer").setVisible(false);
							RootPanel.get("OrderContainer").setVisible(false);
							
							
							chooseButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									List<Integer> dishesId = new ArrayList<Integer>();
									if (cb1.getValue()) {
										dishesId.add(Integer.parseInt(menu.get(0).getId()));
									}
									if (cb2.getValue()) {
										dishesId.add(Integer.parseInt(menu.get(1).getId()));
									}
									if (cb3.getValue()) {
										dishesId.add(Integer.parseInt(menu.get(2).getId()));
									}
									if (cb4.getValue()) {
										dishesId.add(Integer.parseInt(menu.get(3).getId()));
									}
									if (cb5.getValue()) {
										dishesId.add(Integer.parseInt(menu.get(4).getId()));
									}
									logger.log(Level.SEVERE, "dishesid = " + dishesId.toString());
									
									restaurantService.saveOrder(dishesId, clientName,
										new AsyncCallback<Void>() {
											public void onFailure(Throwable caught) {
												errorBox.setText("Put Order Procedure Call - Failure");
												errorBox.center();
												closeButton.setFocus(true);
											}
											public void onSuccess(Void result) {
												logger.log(Level.SEVERE, "Order saved");
												RootPanel.get("MenuContainer").setVisible(false);
												adminButton.setEnabled(true);
											}
										});
								}
							});
						}
					});
			}
		});
		
		
		payButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				restaurantService.getBill(clientName,						
					new AsyncCallback<Bill>() {
						public void onFailure(Throwable caught) {
							logger.log(Level.SEVERE, "get bill error");
							errorBox.setText("ADMIN An error occured in getOrders");
							errorBox.center();
							closeErrorButton.setFocus(true);
						}
						public void onSuccess(final Bill bill) {
							String amount = bill.getAmount();
							logger.log(Level.SEVERE, "got bill " + amount);
							RootPanel.get("OrderContainer").add(new Label("You have an unpaid order"));
							RootPanel.get("OrderContainer").add(new Label("Total amount: " + amount));
															
							final Button payConfirmButton = new Button("pay for it");
							payConfirmButton.addStyleName("payConfirmButton");
							payConfirmButton.setFocus(true);
							RootPanel.get("OrderContainer").add(payConfirmButton);	
							RootPanel.get("OrderContainer").setVisible(true);
							
							payConfirmButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									restaurantService.payBill(clientName,						
										new AsyncCallback<Void>() {
											@Override
											public void onFailure(Throwable caught) {
												errorBox.setText("Pay Bill Procedure Call - Failure");
												errorBox.center();
												closeButton.setFocus(true);
											}

											@Override
											public void onSuccess(Void result) {
												logger.log(Level.SEVERE, "bill is paid");
												RootPanel.get("OrderContainer").setVisible(false);
											}
									
									});
								}
							});
						}
				});
			}			
		});
		
		
		
	}
}
