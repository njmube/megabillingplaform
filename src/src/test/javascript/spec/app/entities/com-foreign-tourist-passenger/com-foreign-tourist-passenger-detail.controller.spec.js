'use strict';

describe('Controller Tests', function() {

    describe('Com_foreign_tourist_passenger Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_foreign_tourist_passenger, MockC_transit_type, MockC_type_road, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_foreign_tourist_passenger = jasmine.createSpy('MockCom_foreign_tourist_passenger');
            MockC_transit_type = jasmine.createSpy('MockC_transit_type');
            MockC_type_road = jasmine.createSpy('MockC_type_road');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_foreign_tourist_passenger': MockCom_foreign_tourist_passenger,
                'C_transit_type': MockC_transit_type,
                'C_type_road': MockC_type_road,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_foreign_tourist_passengerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_foreign_tourist_passengerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
