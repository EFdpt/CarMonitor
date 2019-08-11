package it.uniroma1.fastcharge.carmonitor.app.views.car.chart;

import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.ChartZoomManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class ChartPane extends AnchorPane {	
	private LineChart<Number, Number> carChart;
	private NumberAxis xAxis;
	private NumberAxis yAxis;
	
	private XYChart.Series<Number, Number> APPSSeries, brakeSeries, wheelFLSeries, wheelFRSeries,
								wheelRLSeries, wheelRRSeries, suspensionFLSeries, suspensionFRSeries,
								suspensionRLSeries, suspensionRRSeries, accXSeries, accZSeries;
	
	private Rectangle selectRect;
	private ScrollBar chartScrollBar;
	
	private Region plotArea;

	private ChartZoomManager zoomManager;
	private ChartPanManager panManager;
	
	private Timeline realTimeline;
	private int 	xReal;
	
	private double lastX;
	
	private volatile boolean scrolling;
	
	public ChartPane() {
		super();
		
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		carChart = new LineChart<Number, Number>(xAxis, yAxis);
		AnchorPane.setTopAnchor(carChart, 0.0);
		AnchorPane.setLeftAnchor(carChart, 0.0);
		AnchorPane.setRightAnchor(carChart, 0.0);
		AnchorPane.setBottomAnchor(carChart, 15.0);
		
		selectRect = new Rectangle(0.0, 0.0);
		selectRect.setFill(Color.DODGERBLUE);
		selectRect.setMouseTransparent(true);
		selectRect.setOpacity(0.3);
		selectRect.setStroke(Color.web("#002966"));
		selectRect.setStrokeType(StrokeType.INSIDE);
		selectRect.setStrokeWidth(3.0);
		selectRect.setX(0.0);
		selectRect.setY(0.0);
		AnchorPane.setTopAnchor(selectRect, 0.0);
		AnchorPane.setLeftAnchor(selectRect, 0.0);
		
		chartScrollBar = new ScrollBar();
		AnchorPane.setLeftAnchor(chartScrollBar, 0.0);
		AnchorPane.setRightAnchor(chartScrollBar, 0.0);
		AnchorPane.setBottomAnchor(chartScrollBar, 1.0);
		
		getChildren().add(carChart);
		getChildren().add(selectRect);
		getChildren().add(chartScrollBar);
		
		APPSSeries = new Series<Number, Number>();
        brakeSeries = new Series<Number, Number>();
        wheelFLSeries = new Series<Number, Number>();
        wheelFRSeries = new Series<Number, Number>();
        wheelRLSeries = new Series<Number, Number>();
        wheelRRSeries = new Series<Number, Number>();
        suspensionFLSeries = new Series<Number, Number>();
        suspensionFRSeries = new Series<Number, Number>();
        suspensionRLSeries = new Series<Number, Number>();
        suspensionRRSeries = new Series<Number, Number>();
        accXSeries = new Series<Number, Number>();
        accZSeries = new Series<Number, Number>();
		
		carChart.setCreateSymbols(false);
        carChart.setAnimated(false);
        carChart.setCursor(Cursor.CROSSHAIR);
        
        zoomManager = new ChartZoomManager(this, selectRect, carChart);
        panManager = new ChartPanManager(carChart);
        
        panManager.setMouseFilter(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() != MouseButton.SECONDARY && 
							mouseEvent.getButton() != MouseButton.MIDDLE)
					mouseEvent.consume();
			}
        } );
        
        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(carChart);
        
        chartScrollBar.addEventFilter(MouseEvent.DRAG_DETECTED, (v) -> {
        	scrolling = true;
        });
        
        chartScrollBar.addEventFilter(MouseEvent.MOUSE_DRAGGED, (v) -> {
        	if (!scrolling)
        		return;
        	double axisRange = xAxis.getUpperBound() - xAxis.getLowerBound();
        	lastX = chartScrollBar.getValue();
        	double lower = lastX;
        	double upper = lastX + axisRange;
        	xAxis.setAutoRanging(false);
        	xAxis.setLowerBound(lower);
        	xAxis.setUpperBound(upper);
        });
        
        chartScrollBar.addEventFilter(MouseEvent.MOUSE_RELEASED, (v) -> {
        	if (!scrolling)
        		return;
        	scrolling = false;
        });
        
        chartScrollBar.setDisable(true);
        chartScrollBar.setMin(0.0d);
        
        plotArea = (Region) carChart.lookup("Region");
	}
	
	public void setRealTimeChart() {
		
	}
}
