'use strict';

describe('Controller Tests', function() {

    describe('C_acquired_title Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_acquired_title;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_acquired_title = jasmine.createSpy('MockC_acquired_title');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_acquired_title': MockC_acquired_title
            };
            createController = function() {
                $injector.get('$controller')("C_acquired_titleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_acquired_titleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
