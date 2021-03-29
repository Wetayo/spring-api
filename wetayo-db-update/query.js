//Station
exports.stationMerge = async function (connection, stationResult) {
  stationResult.forEach((station, stationId) => {
    let mobileNumber = station.MOBILE_NO === '' ? -1 : station.MOBILE_NO;

    const stationQuery = `INSERT INTO STATION(STATION_ID,STATION_NAME,CENTER_ID,CENTER_YN,REGION_NAME,MOBILE_NUMBER,GPS,DISTRICT_CODE)
      VALUES (${station.STATION_ID},"${station.STATION_NM}",${station.CENTER_ID},"${station.CENTER_YN}","${station.REGION_NAME}",${mobileNumber},ST_GeomFromText('POINT(${station.Y} ${station.X})'),${station.DISTRICT_CD})
      ON DUPLICATE KEY
      UPDATE
      STATION_ID = ${station.STATION_ID},
      STATION_NAME = "${station.STATION_NM}",
      CENTER_ID = ${station.CENTER_ID},
      CENTER_YN = "${station.CENTER_YN}",
      REGION_NAME = "${station.REGION_NAME}",
      MOBILE_NUMBER = ${mobileNumber},
      GPS = ST_GeomFromText('POINT(${station.Y} ${station.X})'),
      DISTRICT_CODE = ${station.DISTRICT_CD}`;
    connection.query(stationQuery, function (err) {
      if (err) {
        console.log(stationId + '\n' + stationQuery + '\n' + err.stack);
      }
    });
  });
};

exports.routeMerge = async function (connection, routeResult) {
  //Route
  routeResult.forEach((route, index) => {
    let start_station_number = route.ㄴㅆ_STA_NO === '' ? -1 : route.ST_STA_NO;
    let end_station_number = route.ED_STA_NO === '' ? -1 : route.ED_STA_NO;

    const routeQuery = `INSERT INTO ROUTE( ROUTE_ID ,ROUTE_NUMBER ,ROUTE_TP,
    START_STATION_ID,START_STATION_NAME,START_STATION_NUMBER,
    END_STATION_ID,END_STATION_NAME,END_STATION_NUMBER,
    UP_FIRST_TIME,UP_LAST_TIME,DOWN_FIRST_TIME,DOWN_LAST_TIME,
    PEEK_ALLOC,NPEEK_ALLOC,
    COMPANY_ID,COMPANY_NAME,TEL_NUMBER,REGION_NAME,DISTRICT_CODE)
  VALUES (${route.ROUTE_ID} ,"${route.ROUTE_NM}" ,${route.ROUTE_TP},${route.ST_STA_ID},
  "${route.ST_STA_NM}","${start_station_number}",${route.ED_STA_ID},"${route.ED_STA_NM}","${end_station_number}",
  "${route.UP_FIRST_TIME}","${route.UP_LAST_TIME}","${route.DOWN_FIRST_TIME}","${route.DOWN_LAST_TIME}",
  ${route.PEEK_ALLOC},${route.NPEEK_ALLOC},${route.COMPANY_ID},"${route.COMPANY_NM}","${route.TEL_NO}",
  "${route.REGION_NAME}",${route.DISTRICT_CD})
  ON DUPLICATE KEY
  UPDATE
  ROUTE_ID = ${route.ROUTE_ID},
  ROUTE_NUMBER = "${route.ROUTE_NM}",
  ROUTE_TP = ${route.ROUTE_TP},
  START_STATION_ID = ${route.ST_STA_ID},
  START_STATION_NAME = "${route.ST_STA_NM}",
  START_STATION_NUMBER = "${start_station_number}",
  END_STATION_ID = ${route.ED_STA_ID},
  END_STATION_NAME = "${route.ED_STA_NM}",
  END_STATION_NUMBER = "${end_station_number}",
  UP_FIRST_TIME = "${route.UP_FIRST_TIME}",
  UP_LAST_TIME = "${route.UP_LAST_TIME}",
  DOWN_FIRST_TIME = "${route.DOWN_FIRST_TIME}",
  DOWN_LAST_TIME = "${route.DOWN_LAST_TIME}",
  PEEK_ALLOC = ${route.PEEK_ALLOC},
  NPEEK_ALLOC = ${route.NPEEK_ALLOC},
  COMPANY_ID = ${route.COMPANY_ID},
  COMPANY_NAME = "${route.COMPANY_NM}",
  TEL_NUMBER = "${route.TEL_NO}",
  REGION_NAME = "${route.REGION_NAME}",
  DISTRICT_CODE = ${route.DISTRICT_CD}`;

    connection.query(routeQuery, function (err) {
      if (err) {
        console.log(index + '. ' + route.ROUTE_ID + '\n' + routeQuery + '\n' + err.stack);
      }
    });
  });
};

exports.routeStationMerge = async function (connection, routeStationResult) {
  //RouteStation
  routeStationResult.forEach((routeStation, index) => {
    const routeStationQuery = `INSERT INTO ROUTE_STATION( STATION_ID, ROUTE_ID, UP_DOWN, STATION_ORDER, ROUTE_NUMBER,STATION_NAME)
  VALUES (${routeStation.STATION_ID}, ${routeStation.ROUTE_ID}, "${routeStation.UPDOWN}", ${routeStation.STA_ORDER},
  "${routeStation.ROUTE_NM}", "${routeStation.STATION_NM}")
  ON DUPLICATE KEY
  UPDATE
  STATION_ID = ${routeStation.STATION_ID},
  ROUTE_ID = ${routeStation.ROUTE_ID},
  UP_DOWN = "${routeStation.UPDOWN}",
  STATION_ORDER = ${routeStation.STA_ORDER},
  ROUTE_NUMBER = "${routeStation.ROUTE_NM}",
  STATION_NAME = "${routeStation.STATION_NM}"`;

    connection.query(routeStationQuery, function (err) {
      if (err) {
        console.log(index + '. ' + routeStation.ROUTE_ID + ' ' + routeStation.STATION_ID + '\n' + routeStationQuery + '\n' + err.stack);
      }
    });
  });
};
