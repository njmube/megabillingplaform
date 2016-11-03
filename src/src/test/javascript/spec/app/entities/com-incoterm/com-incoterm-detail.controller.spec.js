'use strict';

describe('Controller Tests', function() {

    describe('Com_incoterm Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_incoterm;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_incoterm = jasmine.createSpy('MockCom_incoterm');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_incoterm': MockCom_incoterm
            };
            createController = function() {
                $injector.get('$controller')("Com_incotermDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_incotermUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
