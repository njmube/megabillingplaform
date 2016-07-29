'use strict';

describe('Controller Tests', function() {

    describe('C_transit_type Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_transit_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_transit_type = jasmine.createSpy('MockC_transit_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_transit_type': MockC_transit_type
            };
            createController = function() {
                $injector.get('$controller')("C_transit_typeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_transit_typeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
