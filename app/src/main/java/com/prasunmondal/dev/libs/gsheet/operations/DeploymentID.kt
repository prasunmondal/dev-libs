package com.prasunmondal.dev.libs.gsheet.operations

class DeploymentID {
    companion object {
        /*

        Previous Release Deployments
        ---------------------------------

        AKfycby0rORN4PWBqs2XR2JFTlMHBA2kphA7A8LLrAQ40yF8aiXveEa1cqUJA-glH36VdRZ0Pg




        Delete all release except previous:
        ------------------------------------

        # List of deployment IDs to protect
        PROTECTED_IDS=("AKfycby0rORN4PWBqs2XR2JFTlMHBA2kphA7A8LLrAQ40yF8aiXveEa1cqUJA-glH36VdRZ0Pg"
                       "AKfycbws8KPXUkLBmDnRm2g3_z6V5rOrTDgbS9W9m6Um7k5norXL8SdPFZiYDMxqsqHumK0aaA")


        # Fetch all deployments and process each deployment ID
        clasp deployments | grep 'AKfycb' | awk '{print $2}' | while read id; do
            # Check if the ID is in the protected list
            if [[ " ${PROTECTED_IDS[@]} " =~ " ${id} " ]]; then
                echo "Skipping deployment ID: $id (Protected)"
            else
                echo "Deleting deployment ID: $id"  # Log the ID being deleted
                clasp undeploy $id
            fi
        done


         */



        var deploymentId =
            "AKfycbws8KPXUkLBmDnRm2g3_z6V5rOrTDgbS9W9m6Um7k5norXL8SdPFZiYDMxqsqHumK0aaA"
    }
}
