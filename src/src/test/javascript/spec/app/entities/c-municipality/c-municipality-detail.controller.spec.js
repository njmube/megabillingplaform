'use strict';

describe('Controller Tests', function() {

    describe('C_municipality Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_municipality, MockC_state, MockC_location;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_state = jasmine.createSpy('MockC_state');
            MockC_location = jasmine.createSpy('MockC_location');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_municipality': MockC_municipality,
                'C_state': MockC_state,
                'C_location': MockC_location
            };
            createController = function() {
                $injector.get('$controller')("C_municipalityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_municipalityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
