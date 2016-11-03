'use strict';

describe('Controller Tests', function() {

    describe('Com_legends Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_legends, MockCom_taxlegends;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_legends = jasmine.createSpy('MockCom_legends');
            MockCom_taxlegends = jasmine.createSpy('MockCom_taxlegends');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_legends': MockCom_legends,
                'Com_taxlegends': MockCom_taxlegends
            };
            createController = function() {
                $injector.get('$controller')("Com_legendsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_legendsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
