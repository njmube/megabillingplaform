'use strict';

describe('Controller Tests', function() {

    describe('Free_cfdi Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFree_cfdi, MockCfdi_types, MockCfdi_states, MockFree_emitter, MockFree_reciver, MockPayment_method, MockWay_payment, MockC_money, MockCfdi_type_doc;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            MockCfdi_types = jasmine.createSpy('MockCfdi_types');
            MockCfdi_states = jasmine.createSpy('MockCfdi_states');
            MockFree_emitter = jasmine.createSpy('MockFree_emitter');
            MockFree_reciver = jasmine.createSpy('MockFree_reciver');
            MockPayment_method = jasmine.createSpy('MockPayment_method');
            MockWay_payment = jasmine.createSpy('MockWay_payment');
            MockC_money = jasmine.createSpy('MockC_money');
            MockCfdi_type_doc = jasmine.createSpy('MockCfdi_type_doc');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Free_cfdi': MockFree_cfdi,
                'Cfdi_types': MockCfdi_types,
                'Cfdi_states': MockCfdi_states,
                'Free_emitter': MockFree_emitter,
                'Free_reciver': MockFree_reciver,
                'Payment_method': MockPayment_method,
                'Way_payment': MockWay_payment,
                'C_money': MockC_money,
                'Cfdi_type_doc': MockCfdi_type_doc
            };
            createController = function() {
                $injector.get('$controller')("Free_cfdiDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:free_cfdiUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
