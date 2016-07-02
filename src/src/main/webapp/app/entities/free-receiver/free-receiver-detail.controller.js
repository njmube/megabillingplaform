(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_receiverDetailController', Free_receiverDetailController);

    Free_receiverDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_receiver', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Free_receiverDetailController($scope, $rootScope, $stateParams, entity, Free_receiver, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;
        vm.free_receiver = entity;
        vm.load = function (id) {
            Free_receiver.get({id: id}, function(result) {
                vm.free_receiver = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_receiverUpdate', function(event, result) {
            vm.free_receiver = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
