'use strict';

describe('Controller Tests', function() {

    describe('Client_addres Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockClient_addres, MockC_country, MockC_state, MockC_municipality, MockC_colony, MockC_zip_code;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockClient_addres = jasmine.createSpy('MockClient_addres');
            MockC_country = jasmine.createSpy('MockC_country');
            MockC_state = jasmine.createSpy('MockC_state');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_colony = jasmine.createSpy('MockC_colony');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Client_addres': MockClient_addres,
                'C_country': MockC_country,
                'C_state': MockC_state,
                'C_municipality': MockC_municipality,
                'C_colony': MockC_colony,
                'C_zip_code': MockC_zip_code
            };
            createController = function() {
                $injector.get('$controller')("Client_addresDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:client_addresUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
