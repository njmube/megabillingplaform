'use strict';

describe('Controller Tests', function() {

    describe('Acquired_title Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAcquired_title;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAcquired_title = jasmine.createSpy('MockAcquired_title');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Acquired_title': MockAcquired_title
            };
            createController = function() {
                $injector.get('$controller')("Acquired_titleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:acquired_titleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
