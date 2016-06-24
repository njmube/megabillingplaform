(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_reciverDetailController', Free_reciverDetailController);

    Free_reciverDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_reciver', 'C_country', 'C_state', 'C_municipality', 'C_location', 'C_colony', 'C_zip_code'];

    function Free_reciverDetailController($scope, $rootScope, $stateParams, entity, Free_reciver, C_country, C_state, C_municipality, C_location, C_colony, C_zip_code) {
        var vm = this;
        vm.free_reciver = entity;
        vm.load = function (id) {
            Free_reciver.get({id: id}, function(result) {
                vm.free_reciver = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_reciverUpdate', function(event, result) {
            vm.free_reciver = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
