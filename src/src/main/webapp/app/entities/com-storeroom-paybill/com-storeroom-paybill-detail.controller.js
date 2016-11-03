(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_storeroom_paybillDetailController', Com_storeroom_paybillDetailController);

    Com_storeroom_paybillDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_storeroom_paybill', 'Cfdi'];

    function Com_storeroom_paybillDetailController($scope, $rootScope, $stateParams, entity, Com_storeroom_paybill, Cfdi) {
        var vm = this;
        vm.com_storeroom_paybill = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_storeroom_paybillUpdate', function(event, result) {
            vm.com_storeroom_paybill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
