'use strict';

describe('Controller Tests', function() {

    describe('Com_spei_third Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_spei_third, MockCom_spei, MockCom_payer, MockCom_beneficiary;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_spei_third = jasmine.createSpy('MockCom_spei_third');
            MockCom_spei = jasmine.createSpy('MockCom_spei');
            MockCom_payer = jasmine.createSpy('MockCom_payer');
            MockCom_beneficiary = jasmine.createSpy('MockCom_beneficiary');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_spei_third': MockCom_spei_third,
                'Com_spei': MockCom_spei,
                'Com_payer': MockCom_payer,
                'Com_beneficiary': MockCom_beneficiary
            };
            createController = function() {
                $injector.get('$controller')("Com_spei_thirdDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_spei_thirdUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
