'use strict';

describe('Controller Tests', function() {

    describe('Com_addressee Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_addressee, MockC_country, MockC_state, MockC_municipality, MockC_colony, MockC_zip_code;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_addressee = jasmine.createSpy('MockCom_addressee');
            MockC_country = jasmine.createSpy('MockC_country');
            MockC_state = jasmine.createSpy('MockC_state');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_colony = jasmine.createSpy('MockC_colony');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_addressee': MockCom_addressee,
                'C_country': MockC_country,
                'C_state': MockC_state,
                'C_municipality': MockC_municipality,
                'C_colony': MockC_colony,
                'C_zip_code': MockC_zip_code
            };
            createController = function() {
                $injector.get('$controller')("Com_addresseeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_addresseeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
