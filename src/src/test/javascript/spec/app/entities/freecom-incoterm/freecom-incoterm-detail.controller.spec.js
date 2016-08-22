'use strict';

describe('Controller Tests', function() {

    describe('Freecom_incoterm Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_incoterm;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_incoterm = jasmine.createSpy('MockFreecom_incoterm');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_incoterm': MockFreecom_incoterm
            };
            createController = function() {
                $injector.get('$controller')("Freecom_incotermDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_incotermUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
