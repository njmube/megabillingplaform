'use strict';

describe('Controller Tests', function() {

    describe('C_state Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_state, MockC_country, MockC_municipality;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_state = jasmine.createSpy('MockC_state');
            MockC_country = jasmine.createSpy('MockC_country');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_state': MockC_state,
                'C_country': MockC_country,
                'C_municipality': MockC_municipality
            };
            createController = function() {
                $injector.get('$controller')("C_stateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_stateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
