'use strict';

describe('Controller Tests', function() {

    describe('Free_emitter Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFree_emitter, MockTax_regime, MockC_country, MockC_state, MockC_municipality, MockC_location, MockC_colony, MockC_zip_code;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFree_emitter = jasmine.createSpy('MockFree_emitter');
            MockTax_regime = jasmine.createSpy('MockTax_regime');
            MockC_country = jasmine.createSpy('MockC_country');
            MockC_state = jasmine.createSpy('MockC_state');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_location = jasmine.createSpy('MockC_location');
            MockC_colony = jasmine.createSpy('MockC_colony');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Free_emitter': MockFree_emitter,
                'Tax_regime': MockTax_regime,
                'C_country': MockC_country,
                'C_state': MockC_state,
                'C_municipality': MockC_municipality,
                'C_location': MockC_location,
                'C_colony': MockC_colony,
                'C_zip_code': MockC_zip_code
            };
            createController = function() {
                $injector.get('$controller')("Free_emitterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:free_emitterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
