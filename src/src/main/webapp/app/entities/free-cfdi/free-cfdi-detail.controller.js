(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiDetailController', Free_cfdiDetailController);

    Free_cfdiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_cfdi', 'Cfdi_types', 'Cfdi_states', 'Free_emitter', 'Payment_method', 'C_money', 'Cfdi_type_doc', 'Tax_regime', 'Free_receiver'];

    function Free_cfdiDetailController($scope, $rootScope, $stateParams, entity, Free_cfdi, Cfdi_types, Cfdi_states, Free_emitter, Payment_method, C_money, Cfdi_type_doc, Tax_regime, Free_receiver) {
        var vm = this;
        vm.free_cfdi = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_cfdiUpdate', function(event, result) {
            vm.free_cfdi = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
