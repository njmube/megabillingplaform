'use strict';

describe('Controller Tests', function() {

    describe('Freecom_addressee Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_addressee, MockC_country, MockC_state, MockC_municipality, MockC_colony, MockC_zip_code;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_addressee = jasmine.createSpy('MockFreecom_addressee');
            MockC_country = jasmine.createSpy('MockC_country');
            MockC_state = jasmine.createSpy('MockC_state');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_colony = jasmine.createSpy('MockC_colony');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_addressee': MockFreecom_addressee,
                'C_country': MockC_country,
                'C_state': MockC_state,
                'C_municipality': MockC_municipality,
                'C_colony': MockC_colony,
                'C_zip_code': MockC_zip_code
            };
            createController = function() {
                $injector.get('$controller')("Freecom_addresseeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_addresseeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
