(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_receiverDetailController', Free_receiverDetailController);

    Free_receiverDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_receiver', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', 'Type_taxpayer'];

    function Free_receiverDetailController($scope, $rootScope, $stateParams, entity, Free_receiver, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;
        vm.free_receiver = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_receiverUpdate', function(event, result) {
            vm.free_receiver = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
