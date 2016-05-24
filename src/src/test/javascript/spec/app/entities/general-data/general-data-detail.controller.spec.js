'use strict';

describe('Controller Tests', function() {

    describe('General_data Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGeneral_data;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGeneral_data = jasmine.createSpy('MockGeneral_data');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'General_data': MockGeneral_data
            };
            createController = function() {
                $injector.get('$controller')("General_dataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:general_dataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
