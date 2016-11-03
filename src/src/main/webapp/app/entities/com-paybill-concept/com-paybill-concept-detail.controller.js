(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_paybill_conceptDetailController', Com_paybill_conceptDetailController);

    Com_paybill_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_paybill_concept', 'Com_storeroom_paybill'];

    function Com_paybill_conceptDetailController($scope, $rootScope, $stateParams, entity, Com_paybill_concept, Com_storeroom_paybill) {
        var vm = this;
        vm.com_paybill_concept = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_paybill_conceptUpdate', function(event, result) {
            vm.com_paybill_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
