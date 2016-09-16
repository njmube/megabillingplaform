'use strict';

describe('Controller Tests', function() {

    describe('Cfdi Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCfdi, MockCfdi_states, MockPayment_method, MockCfdi_types, MockCfdi_type_doc, MockC_money, MockCom_tfd, MockTaxpayer_account, MockTax_regime, MockTaxpayer_client;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCfdi = jasmine.createSpy('MockCfdi');
            MockCfdi_states = jasmine.createSpy('MockCfdi_states');
            MockPayment_method = jasmine.createSpy('MockPayment_method');
            MockCfdi_types = jasmine.createSpy('MockCfdi_types');
            MockCfdi_type_doc = jasmine.createSpy('MockCfdi_type_doc');
            MockC_money = jasmine.createSpy('MockC_money');
            MockCom_tfd = jasmine.createSpy('MockCom_tfd');
            MockTaxpayer_account = jasmine.createSpy('MockTaxpayer_account');
            MockTax_regime = jasmine.createSpy('MockTax_regime');
            MockTaxpayer_client = jasmine.createSpy('MockTaxpayer_client');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cfdi': MockCfdi,
                'Cfdi_states': MockCfdi_states,
                'Payment_method': MockPayment_method,
                'Cfdi_types': MockCfdi_types,
                'Cfdi_type_doc': MockCfdi_type_doc,
                'C_money': MockC_money,
                'Com_tfd': MockCom_tfd,
                'Taxpayer_account': MockTaxpayer_account,
                'Tax_regime': MockTax_regime,
                'Taxpayer_client': MockTaxpayer_client
            };
            createController = function() {
                $injector.get('$controller')("CfdiDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:cfdiUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
