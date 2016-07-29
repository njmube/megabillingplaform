'use strict';

describe('Controller Tests', function() {

    describe('C_type_road Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_type_road;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_type_road = jasmine.createSpy('MockC_type_road');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_type_road': MockC_type_road
            };
            createController = function() {
                $injector.get('$controller')("C_type_roadDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_type_roadUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
