'use strict';

describe('Controller Tests', function() {

    describe('Com_info_customs_destruction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_info_customs_destruction, MockCom_destruction_certificate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_info_customs_destruction = jasmine.createSpy('MockCom_info_customs_destruction');
            MockCom_destruction_certificate = jasmine.createSpy('MockCom_destruction_certificate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_info_customs_destruction': MockCom_info_customs_destruction,
                'Com_destruction_certificate': MockCom_destruction_certificate
            };
            createController = function() {
                $injector.get('$controller')("Com_info_customs_destructionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_info_customs_destructionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
