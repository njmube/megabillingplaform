'use strict';

describe('Controller Tests', function() {

    describe('C_colony Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_colony, MockC_location;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_colony = jasmine.createSpy('MockC_colony');
            MockC_location = jasmine.createSpy('MockC_location');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_colony': MockC_colony,
                'C_location': MockC_location
            };
            createController = function() {
                $injector.get('$controller')("C_colonyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_colonyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
