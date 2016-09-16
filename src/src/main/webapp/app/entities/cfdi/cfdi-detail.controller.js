(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiDetailController', CfdiDetailController);

    CfdiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Cfdi', 'Cfdi_states', 'Payment_method', 'Cfdi_types', 'Cfdi_type_doc', 'C_money', 'Com_tfd', 'Taxpayer_account', 'Tax_regime', 'Taxpayer_client'];

    function CfdiDetailController($scope, $rootScope, $stateParams, entity, Cfdi, Cfdi_states, Payment_method, Cfdi_types, Cfdi_type_doc, C_money, Com_tfd, Taxpayer_account, Tax_regime, Taxpayer_client) {
        var vm = this;

        vm.cfdi = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:cfdiUpdate', function(event, result) {
            vm.cfdi = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
