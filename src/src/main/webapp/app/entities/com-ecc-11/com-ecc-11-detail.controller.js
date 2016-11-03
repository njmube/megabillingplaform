(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ecc_11DetailController', Com_ecc_11DetailController);

    Com_ecc_11DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_ecc_11', 'Cfdi'];

    function Com_ecc_11DetailController($scope, $rootScope, $stateParams, entity, Com_ecc_11, Cfdi) {
        var vm = this;
        vm.com_ecc_11 = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_ecc_11Update', function(event, result) {
            vm.com_ecc_11 = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
