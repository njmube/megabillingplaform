'use strict';

describe('Controller Tests', function() {

    describe('Com_accounting Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_accounting, MockCom_ine_entity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_accounting = jasmine.createSpy('MockCom_accounting');
            MockCom_ine_entity = jasmine.createSpy('MockCom_ine_entity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_accounting': MockCom_accounting,
                'Com_ine_entity': MockCom_ine_entity
            };
            createController = function() {
                $injector.get('$controller')("Com_accountingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_accountingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
