## Wetayo-api

Server Endpoint : http://3.35.30.64/wetayo

<br>
<br>

## GraphQL Schema

### ◾ Query

1. getStations

   gps 위도, 경도, 측정 범위를 파라미터로 특정 범위내 정류장과 정류장에 속한 버스번호들 조회

   ```js
   getStations(
       gpsY: Float!
       gpsX: Float!
       distance: Float!
   ): [StationAndRoute]

   type StationAndRoute {
       stationId: Int
       stationName: String
       mobileNumber: String
       distance: Int
       routes: [Route]!
   }
   ```

    - 요청 예시

      ```js
      query{
          getStations(gpsY: 37.3740667, gpsX: 126.8424833, distance: 0.02){
              stationId
              stationName
              mobileNumber
              distance
                routes{
                routeId
                routeNumber
              }
          }
      }
      ```

    - 응답 예시

      ```js
      {
          "data": {
              "getStations": [
                  {
                      "stationId": 224000876,
                      "stationName": "목감호수품애.중흥S클래스",
                      "mobileNumber": "25894",
                      "distance": 0,
                      "routes": [
                          {
                              "routeId": 208000009,
                              "routeNumber": "81"
                          }
                      ]
                  }
              ]
          }
      }
      ```

<br>

2. getRide

   버스기사가 이용하며 해당 정류장에 탑승을 희망하는 고객이 있는지 boolean 형으로 조회

   ```js
   getRide(
       stationId: Int!
       routeId: Int!
   ): Boolean!
   ```

    - 요청 예시

      ```js
      query{
          getRide(stationId: 224000876 routeId: 208000008)
      }
      ```

    - 응답 예시

      ```js
      {
          "data": {
              "getRide": false
          }
      }
      ```

<br>

3. getRoutes

   버스기사가 이용하며 지역이름으로 버스노선들 조회

   ```js
   getRoutes(
       regionName: String!
   ): [Route]

   type Route {
       routeId: Int
       routeNumber: String
   }
   ```

    - 요청 예시

      ```js
      query{
          getRoutes(regionName: "시흥"){
              routeId
              routeNumber
          }
      }
      ```

    - 응답 예시

      ```js
      {
          "data": {
              "getRoutes": [
                  {
                      "routeId": 208000009,
                      "routeNumber": "81"
                  }
              ]
          }
      }
      ```

<br><br>

### ◾ Mutation

1. createRide

   사용자가 특정 정류장의 버스노선을 탑승하고자 선택하는 이벤트

   ```js
   createRide(
       stationId: Int
       routeId: Int
   ): Ride!

   type Ride {
       stationId: Int!
       routeId: Int!
   }
   ```

    - 요청 예시

      ```js
      mutation{
          createRide(stationId: 224000876 routeId:    208000009){
              stationId
              routeId
          }
      }
      ```

    - 응답 예시

      ```js
      {
          "data": {
              "createRide": {
                  "stationId": 224000876,
                  "routeId": 208000009
              }
          }
      }
      ```

<br>

2. deleteRide

   기사가 이용하며 탑승희망자를 삭제하는 이벤트

   ```js
   deleteRide(
       stationId: Int
       routeId: Int
   ): Boolean!
   ```

    - 요청 예시

      ```js
      mutation{
          deleteRide(stationId: 224000876 routeId: 208000009)
      }
      ```

    - 응답 예시

      ```js
      {
          "data": {
              "deleteRide": true
          }
      }
      ```

<br>

### ◾ 상태 코드

#### 실패 코드
1. 400

잘못된 요청 (Validation Error)
   
   - 응답 예시
      ```json
      {
         "errors": [
            {
               "message": "Validation Error : Bad Request",
               "extensions": {
                  "errorCode": "400",
                  "classification": "ValidationError"
               }
            }
         ], 
        "data": null
      }
      ```
     
<br>

2. 404 

Not Found Error

   - 응답 예시
      ```json
       {
         "errors": [
            {
               "message": "(Mutation)Insert Exception : Not Found Id",
               "extensions": {
                  "errorCode": "404",
                  "classification": "DataFetching Error"
               }
            }
         ],
         "data": {
            "createRide": null
         }
      }
      ```

<br>     

3. 430

Aleady Insert Error

   - 응답 예시
      ```json
      {
         "errors": [
            {
               "message": "(Mutation)Insert Exception : Already insert",
               "extensions": {
                  "errorCode": "430",
                  "classification": "DataFetching Error"
               }  
            }
         ],
         "data": {
            "createRide": null
         }
      }
      ```